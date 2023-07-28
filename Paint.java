/******************************************************************************************\
 * Title: A Java program that allows the user to draw lines, rectangles and ovals.
 * 
 * @author Kirandeep Singh
\******************************************************************************************/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * Driver class to run 'MyCustomPaint' class.
 * 
 * @author Kirandeep Singh
 *
 */
public class Paint {
	/**
	 * Driver function
	 * 
	 * @param 	args 			Command line arguments passed
	 * @throws 	IOException 	If the input data type is invalid.
	 */
	public static void main(String[] args) {
		new MyCustomPaint();
	}
}

/**
 * Class to store data of shape
 * @author     Kirandeep Singh
 */
class Shape {

	static public final int TYPE_LINE = 0;
	static public final int TYPE_RECTANGLE = 1;
	static public final int TYPE_OVAL = 2;

	int shapeType;
	int x1, y1, x2, y2;

	Shape(int shapeType, int x1, int y1, int x2, int y2) {
		if (this.shapeType < 0 || this.shapeType > 2) {
			throw new IllegalArgumentException("Invalid type of shape");
		}
		this.shapeType = shapeType;

		this.x1 = x1;
		this.x2 = x2;

		this.y1 = y1;
		this.y2 = y2;
	}

	/**
	 * Draws shape on the graphicd
	 *
	 * @param      g     Graphics of component to draw things on
	 */
	public void draw(Graphics g) {
		switch (this.shapeType) {
			case TYPE_LINE:
				g.drawLine(x1, y1, x2, y2);
				break;

			case TYPE_RECTANGLE:
				g.drawRect(x1, y1, x2 - x1, y2 - y1);
				break;

			case TYPE_OVAL:
				g.drawOval(x1, y1, x2 - x1, y2 - y1);
				break;
		}
	}
}


/**
 * Frame to draw paint objects
 * @author     Kirandeep Singh
 */
class MyCustomPaint extends JFrame implements ActionListener {
	boolean toggle;

	int x = 0;

	JMenuBar menuBar;
	JMenu menu;

	JMenuItem menuItemLine;
	JMenuItem menuItemRectangle;
	JMenuItem menuItemOval;
	JMenuItem menuItemClear;

	List<Shape> shapes;
	Shape currentShape;

	JPanel canvas;

	MyCustomPaint() {
		setTitle("MyCustomPaint");
		setLayout(new BorderLayout());
		shapes = new ArrayList<Shape>();

		currentShape = new Shape(Shape.TYPE_LINE, 0, 0, 0, 0);

		canvas = new JPanel() {

			@Override
			public void paint(Graphics g) {
				super.paint(g);

				for (Shape shape : shapes) {
					shape.draw(g);
				}
			}
		};

		canvas.setForeground(Color.RED);

		canvas.addMouseMotionListener(new MouseMotionListener() {
			boolean isDrawing = false;

			@Override
			public void mouseDragged(MouseEvent e) {

				currentShape.x2 = e.getX();
				currentShape.y2 = e.getY();

				canvas.paint(canvas.getGraphics());
				currentShape.draw(canvas.getGraphics());

				isDrawing = true;
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if(isDrawing){
					shapes.add(currentShape);
					canvas.paint(canvas.getGraphics());

					isDrawing = false;
				}else{
					currentShape = new Shape(currentShape.shapeType, 0, 0, 0, 0);
					currentShape.x1 = e.getX();
					currentShape.y1 = e.getY();
				}
			}
		});

		menuBar = new JMenuBar();
		menu = new JMenu("Shape");

		menuItemLine = new JMenuItem("Line");
		menuItemRectangle = new JMenuItem("Rectangle");
		menuItemOval = new JMenuItem("Oval");
		menuItemClear = new JMenuItem("Clear");

		menuItemLine.addActionListener(this);
		menuItemRectangle.addActionListener(this);
		menuItemOval.addActionListener(this);
		menuItemClear.addActionListener(this);

		menuBar.add(menu);

		menu.add(menuItemLine);
		menu.add(menuItemRectangle);
		menu.add(menuItemOval);
		menu.add(new JSeparator());
		menu.add(menuItemClear);

		add(menuBar, BorderLayout.NORTH);
		add(canvas);

		setSize(400, 300);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	///////////// ACTION EVENTS ////////////////

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
			case "Line"      : currentShape.shapeType = Shape.TYPE_LINE;      break;
			case "Rectangle" : currentShape.shapeType = Shape.TYPE_RECTANGLE; break;
			case "Oval"      : currentShape.shapeType = Shape.TYPE_OVAL;      break;

			case "Clear"     : shapes = new ArrayList<Shape>(); canvas.paint(canvas.getGraphics());
		}

	}
}
