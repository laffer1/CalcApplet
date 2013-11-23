import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.ListIterator;


/**
 * Calculator Applet
 * COSC 436: Assignment 5
 * Date: December 13, 2006
 *
 * NOTE: This uses enumerators and as such must be compiled
 * with JDK 1.5.x "Java 5".  Java 5 is available on Macintosh
 * systems and is the current supported version in Windows XP.
 *
 * @author Lucas Holt
 * @version 1.0
 */
public class CalcApplet extends JApplet
              implements ActionListener {

    /* The operations available for our calculator. */
    enum Operation { ADD, SUBTRACT, MULTIPLY, DIVIDE, OTHER };

    /**
     * Perform numeric calculations similar to a very cheap
     * Calculator.
     */
    class Calculator {

        private double total;     // The current running total.
        private double lastvalue; // the last value entered.
        private Operation lastop; // the last operation performed.
        private boolean multiequal; // has equal been hit n > 1 times

        /**
         * Repeat the last operation with the value specified.
         */
        protected void doLastOpWith(double x) {
            switch (lastop) {
                case ADD:
                    total = total +x;
                    break;
                case SUBTRACT:
                    total = total -x;
                    break;
                case MULTIPLY:
                    total = total * x;
                    break;
                case DIVIDE:
                    total = total / x;
                    break;
                default:
                    // do nothing, never get here.
                    break;
            }
        }

        /**
         * Repeat the last operation with the saved last
         * value.
         */
        protected void repeatLastOperation() {
            switch (lastop) {
                case ADD:
                    add(lastvalue);
                    break;
                case SUBTRACT:
                    subtract(lastvalue);
                    break;
                case MULTIPLY:
                    multiply(lastvalue);
                    break;
                case DIVIDE:
                    divide(lastvalue);
                    break;
                default:
                    // do nothing, never get here.
                    break;
            }
        }

        /**
         * Retain the last operation and value used.
         * @param op
         */
        protected void setOperation(Operation op, double x) {
            lastop = op;
            lastvalue = x;
            multiequal = false;
        }

        /**
         * Create a calculator with an initial starting value
         * as specified.
         * @param x initial value to start from
         */
        public Calculator(double x) {
            total = x;
            lastop = Operation.OTHER;
            multiequal = false;
        }

        /**
         * Add the value to the total.
         * @param x number to add
         */
        public void add(double x) {
            total = total + x;
            setOperation(Operation.ADD, x);
        }

        /**
         * Subtract the value from the total.
         * @param x value to subtract off total.
         */
        public void subtract(double x) {
            total = total - x;
            setOperation(Operation.SUBTRACT, x);
        }

        /**
         * Multiply the value times the current total.
         * @param x value to multiply
         */
        public void multiply(double x) {
            total = total *x;
            setOperation(Operation.MULTIPLY, x);
        }

        /**
         * Divide the total by this value.
         * @param x divisor
         */
        public void divide(double x) {
            total = total / x;
            setOperation(Operation.DIVIDE, x);
        }

        /**
         * Get the current total remembering state
         * @return The current total for the calculator is returned
         * and on future calls, the last operation is repeated.
         */
        public double equal() {
            repeatLastOperation();
            multiequal = true;
            return total;
        }

        /**
         * Get the current total ignoring state.
         * @return The curernt total for the calculator is returned.
         */
        public double equalNoSave() {
            return total;
        }

        /**
         * Reset the calculator to its initial on state.
         */
        public void clear() {
            total = 0;
            lastop= Operation.OTHER;
            multiequal = false;
        }

        /**
         * Changes the total accumulated in the calculator without
         * changing the operating state.
         * @param x value to change the total to.
         */
        public void setTotal(double x) {
            total = x;
        }

    }

    private Calculator calc; // our compute code

    JTextField display;      // "LCD" output display
    JPanel buttonPanel;      // Panel with all our buttons

    private GridBagLayout buttonLayout;
    private GridBagConstraints c;

    private ArrayList blist; // list of buttons in buttonPanel.

    private Operation op = Operation.OTHER;  // initial operation.
    private String operation = ""; // checks our state, could be printed.

    private boolean firstTime = true;  // first use since op or at start

    public void init(){
        blist = new ArrayList();
        calc = new Calculator(0);

        setLayout(new GridLayout(1,0));

        buttonPanel = new JPanel();

        c = new GridBagConstraints();
        buttonLayout = new GridBagLayout();
        buttonPanel.setLayout(buttonLayout);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(3, 3, 3, 3);

        add(buttonPanel);

        // setup "LCD" display
        display = new JTextField();
        display.setEditable(false);
        display.setBackground(Color.yellow);
        display.setText("0.0");
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Serif", Font.PLAIN, 16));

        // setup location of display and add.
        c.weightx = c.weighty = 10;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        c.gridheight = 1;
        buttonLayout.setConstraints(display, c);
        buttonPanel.add(display);

        // add all buttons.
        myButton(buttonLayout,buttonPanel,"7",0,1,1,1,10);
        myButton(buttonLayout,buttonPanel,"8",1,1,1,1,10);
        myButton(buttonLayout,buttonPanel,"9",2,1,1,1,10);
        myButton(buttonLayout,buttonPanel,"/",3,1,1,1,10);

        myButton(buttonLayout,buttonPanel,"4",0,2,1,1,10);
        myButton(buttonLayout,buttonPanel,"5",1,2,1,1,10);
        myButton(buttonLayout,buttonPanel,"6",2,2,1,1,10);
        myButton(buttonLayout,buttonPanel,"*",3,2,1,1,10);

        myButton(buttonLayout,buttonPanel,"1",0,3,1,1,10);
        myButton(buttonLayout,buttonPanel,"2",1,3,1,1,10);
        myButton(buttonLayout,buttonPanel,"3",2,3,1,1,10);
        myButton(buttonLayout,buttonPanel,"+",3,3,1,1,10);

        myButton(buttonLayout,buttonPanel,"0",0,4,1,1,10);
        myButton(buttonLayout,buttonPanel,"=",1,4,1,1,10);
        myButton(buttonLayout,buttonPanel,"C",2,4,1,1,10);
        myButton(buttonLayout,buttonPanel,"-",3,4,1,1,10);

        // set the ideal size.
        resize(300,300);
      }

     public void start(){
        System.out.println("Applet starting.");
     }

     public void stop(){
        System.out.println("Applet stopping.");
     }

     public void destroy(){
        System.out.println("Destroy method called.");
     }


     public void actionPerformed(ActionEvent event){

         Object source = event.getSource();

         /* if the event isn't a button press, we
            want t o ignore it */
         if (!(source instanceof JButton))
             return;

         // all the buttons are stored in an arraylist so
         // that we can compare them to know who sent the event.
         ListIterator li = blist.listIterator();

         JButton b;
         while ((b = (JButton)li.next()) != null) {
             if (source.equals(b)) {

                 if (b.getName().equals("C")) {
                     // the clear button
                     calc.clear();
                     operation = "";
                     display.setText("0.0");
                     firstTime = true;
                 } else if (b.getName().equals("+")) {
                     // add
                    if (firstTime) {
                        calc.add(Double.valueOf(display.getText()));
                        op = Operation.ADD;
                        operation = "+";
                        display.setText(new Double(calc.equalNoSave()).toString());
                        firstTime = false;
                    } else {
                        performLastOp();
                        op = Operation.ADD;
                        operation = "+";
                        display.setText(new Double(calc.equalNoSave()).toString());

                    }
                 } else if (b.getName().equals("-")) {
                     // subtract
                     if (firstTime) {
                        calc.add(Double.valueOf(display.getText()));
                        op = Operation.SUBTRACT;
                        operation = "-";
                        display.setText(new Double(calc.equalNoSave()).toString());
                        firstTime = false;
                     } else {
                        performLastOp();
                        op = Operation.SUBTRACT;
                        operation = "-";
                        display.setText(new Double(calc.equalNoSave()).toString());

                    }
                 } else if (b.getName().equals("*")) {
                     // multiply
                     if (firstTime) {
                        calc.add(Double.valueOf(display.getText()));
                        op = Operation.MULTIPLY;
                        operation = "*";
                        display.setText(new Double(calc.equalNoSave()).toString());
                        firstTime = false;
                     } else {
                        performLastOp();
                        op = Operation.MULTIPLY;
                        operation = "*";
                        display.setText(new Double(calc.equalNoSave()).toString());

                     }
                 } else if (b.getName().equals("/")) {
                     // divide
                     if (firstTime) {
                        calc.add(Double.valueOf(display.getText()));
                        op = Operation.DIVIDE;
                        operation = "/";
                        display.setText(new Double(calc.equalNoSave()).toString());
                        firstTime = false;
                     } else {
                        performLastOp();
                        op = Operation.DIVIDE;
                        operation = "/";
                        display.setText(new Double(calc.equalNoSave()).toString());

                     }
                 } else if (b.getName().equals("=")) {
                     // equal
                      if (firstTime) {
                        calc.add(Double.valueOf(display.getText()));
                        op = Operation.OTHER;
                        display.setText(new Double(calc.equalNoSave()).toString());
                        firstTime = false;
                    } else {
                        performLastOp();
                        op = Operation.OTHER;
                        display.setText(new Double(calc.equalNoSave()).toString());
                    }
                    operation = "=";
                 } else {
                     // a number key was pressed.

                     if (display.getText().equals("0") ||
                             display.getText().equals("0.0"))
                             display.setText(b.getName());
                     else
                         if (operation.equals(""))
                             display.setText(display.getText() + b.getName());
                         else {
                             operation = "";
                             display.setText(b.getName());
                         }
                 }
                 break;
             }
         }
   }

    /**
     * Perform the last operation requested.
     */
    protected void performLastOp() {
        switch (op) {
            case ADD:
                calc.add(Double.valueOf(display.getText()));
                break;
            case SUBTRACT:
                calc.subtract(Double.valueOf(display.getText()));
                break;
            case MULTIPLY:
                calc.multiply(Double.valueOf(display.getText()));
                break;
            case DIVIDE:
                calc.divide(Double.valueOf(display.getText()));
                break;
            default:
                // never get here
                break;
        }
    }


    /**
     * Creates all the buttons for the calculator dynamically and includes a
     * reference to them for later comparison.
     * @param gb    The layout
     * @param panel The panel these will belong to.
     * @param label Text appearing on the button.
     * @param gridx Horizontal location
     * @param gridy Vertical location
     * @param width How wide the button is
     * @param height How tall the button is
     * @param weight The weight for determining drawing priorty
     */
    public void myButton(GridBagLayout gb, JPanel panel, String label,
                         int gridx, int gridy, int width, int height, int weight)
    {
        c.weightx =c.weighty = weight;
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = width;
        c.gridheight = height;

        JButton newButton = new JButton(label);
        newButton.setName(label);
        gb.setConstraints(newButton, c);
        panel.add(newButton);
        newButton.addActionListener(this);
        blist.add(newButton);
    }
}
