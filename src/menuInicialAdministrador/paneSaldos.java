package menuInicialAdministrador;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;
import java.math.BigDecimal; // Para BigDecimal

// JFreeChart Imports
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; // Para mostrar el gráfico en un JPanel
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset; // Para datos de Pie Chart
import org.jfree.data.category.DefaultCategoryDataset; // Para datos de Bar Chart

import DAO.ReportesPagosDAO;
import util.ConnectionADMIN; // Tu clase de conexión

public class paneSaldos extends JPanel {

    private static final long serialVersionUID = 1L;

    private ReportesPagosDAO pagosDAO;

    /**
     * Create the panel.
     */
    public paneSaldos() {
        pagosDAO = new ReportesPagosDAO(); // Inicializar el DAO

        setLayout(new BorderLayout(10, 10)); // Usar BorderLayout para organizar gráficos
        setBackground(new Color(255, 235, 215)); // Color de fondo del panel

        JLabel lblTitulo = new JLabel("Reportes Financieros de Pagos");
        lblTitulo.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(255, 128, 64)); // Un color que combine
        add(lblTitulo, BorderLayout.NORTH);

        // Panel para contener los gráficos
        JPanel panelGraficos = new JPanel();
        panelGraficos.setLayout(new GridLayout(2, 1, 10, 10)); // GridLayout para 2 gráficos, uno encima del otro
        panelGraficos.setBackground(getBackground()); // Mismo color de fondo
        add(panelGraficos, BorderLayout.CENTER);

        // Cargar y mostrar los gráficos
        cargarYMostrarGraficos(panelGraficos);
    }

    private void cargarYMostrarGraficos(JPanel panelGraficos) {
        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
            // --- Gráfico de Pastel (Pie Chart) - Distribución por Estado de Pago ---
            Map<String, Integer> conteoEstados = pagosDAO.obtenerConteoPagosPorEstado(conn);
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            for (Map.Entry<String, Integer> entry : conteoEstados.entrySet()) {
                pieDataset.setValue(entry.getKey(), entry.getValue());
            }

            JFreeChart pieChart = ChartFactory.createPieChart(
                "Distribución de Pagos por Estado", // Título del gráfico
                pieDataset,                      // Datos
                true,                            // Leyenda
                true,                            // Tooltips
                false                            // URLs
            );
            ChartPanel pieChartPanel = new ChartPanel(pieChart);
            pieChartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
            pieChartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno
            pieChartPanel.setBackground(Color.WHITE); // Fondo del gráfico
            panelGraficos.add(pieChartPanel);


            // --- Gráfico de Barras (Bar Chart) - Valores Agregados por Cliente ---
            List<Map<String, Object>> valoresPorCliente = pagosDAO.obtenerValoresAgregadosPorCliente(conn);
            DefaultCategoryDataset barDataset = new DefaultCategoryDataset();

            for (Map<String, Object> fila : valoresPorCliente) {
                String idCliente = "Cliente " + fila.get("id_cliente");
                BigDecimal totalTrabajo = (BigDecimal) fila.get("total_trabajo");
                BigDecimal totalPagado = (BigDecimal) fila.get("total_pagado");
                BigDecimal totalMora = (BigDecimal) fila.get("total_mora");

                barDataset.addValue(totalTrabajo, "Valor Trabajo", idCliente);
                barDataset.addValue(totalPagado, "Valor Pagado", idCliente);
                barDataset.addValue(totalMora, "Valor Mora", idCliente);
            }

            JFreeChart barChart = ChartFactory.createBarChart(
                "Valores de Pagos por Cliente", // Título del gráfico
                "Cliente",                   // Etiqueta del eje X
                "Monto ($)",                 // Etiqueta del eje Y
                barDataset,                  // Datos
                org.jfree.chart.plot.PlotOrientation.VERTICAL, // Orientación
                true,                        // Leyenda
                true,                        // Tooltips
                false                        // URLs
            );
            ChartPanel barChartPanel = new ChartPanel(barChart);
            barChartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
            barChartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno
            barChartPanel.setBackground(Color.WHITE); // Fondo del gráfico
            panelGraficos.add(barChartPanel);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos para los gráficos: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            // Mostrar un mensaje en el panel si no se pueden cargar los gráficos
            panelGraficos.removeAll(); // Quita los paneles de gráficos vacíos
            panelGraficos.setLayout(new BorderLayout()); // Cambia a BorderLayout para el mensaje
            JLabel errorMessage = new JLabel("<html><center>No se pudieron cargar los gráficos.<br>Verifique la conexión a la base de datos o los datos disponibles.</center></html>");
            errorMessage.setFont(new Font("Tahoma", Font.PLAIN, 16));
            errorMessage.setForeground(Color.RED);
            errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
            panelGraficos.add(errorMessage, BorderLayout.CENTER);
            panelGraficos.revalidate();
            panelGraficos.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado al generar gráficos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}