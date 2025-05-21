package conectionFrames;

import java.awt.Frame;

import javax.swing.JFrame;

import login.intefaceLogin;
import login.intefaceSingin;

public class conectarFrames {

	public static void AbrirFrameLogin(Frame frame) {
		new intefaceLogin().setVisible(true);
		frame.setVisible(false);
	}
	
	public static void AbrirFrameSingin(Frame frame) {
		new intefaceSingin().setVisible(true);
		frame.setVisible(false);
	}
}
