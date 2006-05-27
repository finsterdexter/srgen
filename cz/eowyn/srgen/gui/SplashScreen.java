/*
 * SplashScreen.java
 * Copyright 2001 (C) Mario Bonassin
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Created on April 21, 2001, 2:15 PM
 */

package cz.eowyn.srgen.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.JFrame;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.io.IOException;


import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Timer;

import cz.eowyn.srgen.gui.utils.IconUtilitities;

/**
 * <code>SplashScreen</code> creates a splash screen
 *
 * @author zebuleon
 * @version $Revision$
 */

public final class SplashScreen extends JWindow
{
	public class FadeImagePanel extends JPanel {
        private Image image;   
        private Image bgImage;
        private float alpha = 0.0f;
        private Dimension PREF_SIZE;
        
        FadeImagePanel(URL imageLocation) throws IOException {
            this.image = ImageIO.read(imageLocation);
            initialize();
        }
        
        private void initialize() {
            captureBackground();
            startAnimation();
        }
 
        private void startAnimation() {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (alpha < 1.0f) {
                        try {
                            Thread.sleep(20);
                            repaint();
                            alpha += 0.05f;
                            if (alpha > 1.0f) {
                                alpha = 1.0f;
                            }
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }
                    }
                }
            });
            t.start();
        }
        
        public Point getPreferredSplashLocation() {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int w = screenSize.width;
            int h = screenSize.height;
            int x = (w - image.getWidth(this))/2;
            int y = (h - image.getHeight(this))/2;
            return new Point(x,y);
        }
 
        private void captureBackground() {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int w = screenSize.width;
            int h = screenSize.height;
            int x = (w - this.getPreferredSize().width)/2;
            int y = (h - this.getPreferredSize().height)/2;
            Rectangle splashRectangle = new Rectangle(x,y,w,h);
            try {
                Robot r = new Robot();
                bgImage = r.createScreenCapture(splashRectangle);
            } catch (AWTException awte) {
                awte.printStackTrace();
            }
        }
        
//        protected void paintComponent(Graphics g) {
//            Graphics2D g2d = (Graphics2D)g;
//            g2d.drawImage(bgImage, 0, 0, this);
//            AlphaComposite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
//            g2d.setComposite(comp);
//            g.drawImage(image, 0, 0, this);
//            System.err.println("x");
//        }
        
        public Dimension getPreferredSize() {
            if (PREF_SIZE == null) {
                PREF_SIZE = new Dimension(image.getWidth(this), image.getHeight(this));
            }
            return PREF_SIZE;
        }
	}
        
	public SplashScreen()
	{
		super ();
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		
		URL imageURL = FadeImagePanel.class.getResource(IconUtilitities.RESOURCE_URL + "SplashScreen.gif");
        FadeImagePanel fip;
        try {
        	fip = new FadeImagePanel(imageURL);
        } catch (IOException u) {
        	System.err.println("GRRRRR");
        	return;
        }
        
        add(fip);
        pack();
        
        setLocation(fip.getPreferredSplashLocation());
        setVisible(true);
        new Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //dispose ();
            }
        }).start();
    }
		
//		JPanel splash = new JPanel(new BorderLayout(12, 12));
//		splash.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, Color.black), new EmptyBorder(12, 12, 12, 12)));
//		splash.setBackground(Color.black);
//
//		URL url = getClass().getResource(IconUtilitities.RESOURCE_URL + "SplashScreen.gif");
//
//		if (url != null)
//		{
//			
//			JLabel label = new JLabel(new ImageIcon(url));
//			//JLabel label = new JLabel("grrr");
//			splash.add(label, BorderLayout.CENTER);
//		}
//
//		//setContentPane(splash);
//		add (splash);
//		pack();
//
//		Dimension screen = getToolkit().getScreenSize();
//		setLocation((screen.width - getSize().width) / 2, (screen.height - getSize().height) / 2);
//		setVisible(true);
//	}
	
}
