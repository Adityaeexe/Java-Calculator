
import java.awt;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event;
import java.awt.event.ActionListener;

public class MyCalculator extends Frame{
    public boolean setClear = true;
    double number,memvalue;
    char op;

    String numbersText[] = {"7","8","9","4","5","6","1","2","3","0","+/-","."};
    String symbolsText[] = {"/","sqrt","*","%","-","1/x","+","="};
    String memoryText[] = {"MC","MR","MS","M+"};
    String specialText[] = {"Backspc","C","CE"};

    MyNumbers numbers[] = new MyNumbers[numbersText.length];
    MySymbols symbols[] = new MySymbols[symbolsText.length];
    MyMemory memory[] = new MyMemory[memoryText.length];
    MySpecial special[] = new MySpecial[specialText.length];

    Label displayLabel = new Label("0", Label.RIGHT);
    Label memLabel = new Label("",Label.RIGHT);

    final int FRAME_WIDTH=325,FRAME_HEIGHT=325;
    final int HEIGHT=30,WIDTH=30,H_SPACE=10,V_SPACE=10;
    final int TOPX=20, TOPY=50;

    MyCalculator(String frameText) // constructor
    {
        super(frameText);

        int tempX=TOPX, y=TOPY;
        displayLabel.setBounds(tempX,y,240,HEIGHT);
        displayLabel.setBackground(Color.BLUE);
        displayLabel.setForeground(Color.WHITE);
        add(displayLabel);
        
        // set CO-ORDINATES for Memory Buttons
        
        tempX=TOPX;
        y=TOPX+2*(HEIGHT+V_SPACE);
        for(int i=0;i<memory.length;i++)
        {
            memory[i] = new MyMemory(tempX,y,WIDTH,HEIGHT,memoryText,this);
            memory[i].setForeground(Color.RED);
            y+=HEIGHT+V_SPACE;

        }

        //set CO-ORDINATES FOR SPECIAL BUTTONS

        tempX=topx+1*(WIDTH+H_SPACE);y=TOPY+1*(HEIGHT+V_SPACE);
        for(int i=0;i<special.length;i++)
        {
            special[i]= new MySpecial(tempX,y,WIDTH*2,HEIGHT,specialText[i],this);
            special[i].setForeground(Color.RED);
            tempX=tempX+2*WIDTH+H_SPACE;

        }

        // set CO-ORDINATES for Digit/numbers

        int numbersX = TOPX + WIDTH+H_SPACE;
        int numbersY = TOPY + 2*(HEIGHT+V_SPACE);
        tempX = numbersX; y = numbersY;
        for(int i=0;i<numbers.length;i++)
        {
            numbers[i]= new MyNumbers(tempX,y,WIDTH,HEIGHT,numbersText[i],this);
            numbers[i].setForeground(Color.BLUE);
            tempX+=WIDTH+H_SPACE;
            if((i+1)%3==0){tempX=numbersX; y+=HEIGHT+V_SPACE;}
        }

        // set CO-ORDINATES for Operators/symbols

        int symX = numbersX + 2*(WIDTH + H_SPACE)+H_SPACE;
        int symY = numbersY;
        tempX = symX; y=symY;
        for(int i =0;i<symbols.length;i++);
        {
            tempX+=WIDTH+H_SPACE;
            symbols[i]=new MySymbols(tempX,y,WIDTH,HEIGHT,symbolsText[i],this);
            symbols[i].setForeground(Color.RED);
            if((i+1)%2==0){tempX=symX;y+=HEIGHT+V_SPACE;}
        }

        addWindowListen(new WindowAdapter()
        {
            public void windowClosing(WindowEvent ev)
            {System.exit(0);}

        });

        SetLayout(null);
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        setVisible(true);
    }

    ////////

    static String getFormatedText(double temp)
    {
        String resText=""+temp;
        if(resText.lastIndexOf(".0")>0)
        resText=resText.substring(0,resText.length()-2);
        return resText;
    }

    ///////////

    public static void main(String[]args)
    {
        new MyCalculator("");
    }
}

/*********************************/

class MyNumbers extends Button implements ActionListener
{
    MyCalculator cl;

    //////////////////////////

    MyNumbers(int x, int y, int width, int height,String cap, MyCalculator clc)
{
    super(cap);
    setBounds(x,y,width,height);
    this.cl=clc;
    this.cl.add(this);
    addActionListener(this);

}
///////////////////////////
static boolean isInString(String s, char ch)
{
    for(int i=0;i<s.length();i++)if(s.charAt(i)==ch)return true;
    return false;
}
//////////////////////////
public void actionPerformed(ActionEvent ev)
{
    String tempText = ((MyNumbers)ev.getSource()).getLabel();

    if(tempText.equals("."))
    {
        if(cl.setClear)
        {cl.displayLabel.setText("0");cl.setClear=false;}
        else if(!isInString(cl.displayLabel.getText(),'.'));
        cl.displayLabel.setText(cl.displayLabel.getText()+".");
        return;
    }

    int index = 0;
    try{
        index=Integer.parseInt(tempText);   
    }catch(NumberFormatException e){return;}
    
    if(index==0 && cl.displayLabel.getText().equals("0"))return;

    if(cl.setClear)
    {cl.displayLabel.setText(""+index);cl.setClear=false;}
    else
    cl.displayLabel.setText(cl.displayLabel.getText()+index);

}//actionperformed
}//classDefination

/***************************/

class MySymbols extends Button implements ActionListener  
{  
MyCalculator cl;  
  
MySymbols(int x,int y, int width,int height,String cap, MyCalculator clc)  
{  
super(cap);  
setBounds(x,y,width,height);  
this.cl=clc;  
this.cl.add(this);  
addActionListener(this);  
}  
///////////////////////  
public void actionPerformed(ActionEvent ev)  
{  
String opText=((MySymbols)ev.getSource()).getLabel();  
  
cl.setClear=true;  
double temp=Double.parseDouble(cl.displayLabel.getText());  
  
if(opText.equals("1/x"))  
    {  
    try  
        {double tempd=1/(double)temp;  
        cl.displayLabel.setText(MyCalculator.getFormattedText(tempd));}  
    catch(ArithmeticException excp)  
                        {cl.displayLabel.setText("Divide by 0.");}  
    return;  
    }  
if(opText.equals("sqrt"))  
    {  
    try  
        {double tempd=Math.sqrt(temp);  
        cl.displayLabel.setText(MyCalculator.getFormattedText(tempd));}  
            catch(ArithmeticException excp)  
                    {cl.displayLabel.setText("Divide by 0.");}  
    return;  
    }  
if(!opText.equals("="))  
    {  
    cl.number=temp;  
    cl.op=opText.charAt(0);  
    return;  
    }  
// process = button pressed  
switch(cl.op)  
{  
case '+':  
    temp+=cl.number;break;  
case '-':  
    temp=cl.number-temp;break;  
case '*':  
    temp*=cl.number;break;  
case '%':  
    try{temp=cl.number%temp;}  
    catch(ArithmeticException excp)  
        {cl.displayLabel.setText("Divide by 0."); return;}  
    break;  
case '/':  
    try{temp=cl.number/temp;}  
        catch(ArithmeticException excp)  
                {cl.displayLabel.setText("Divide by 0."); return;}  
    break;  
}//switch  
  
cl.displayLabel.setText(MyCalculator.getFormattedText(temp));  
//cl.number=temp;  
}//actionPerformed  
}//class  
  
/****************************************/  
  
class MyMemoryButton extends Button implements ActionListener  
{  
MyCalculator cl;  
  
/////////////////////////////////  
MyMemoryButton(int x,int y, int width,int height,String cap, MyCalculator clc)  
{  
super(cap);  
setBounds(x,y,width,height);  
this.cl=clc;  
this.cl.add(this);  
addActionListener(this);  
}  
////////////////////////////////////////////////  
public void actionPerformed(ActionEvent ev)  
{  
char memop=((MyMemoryButton)ev.getSource()).getLabel().charAt(1);  
  
cl.setClear=true;  
double temp=Double.parseDouble(cl.displayLabel.getText());  
  
switch(memop)  
{  
case 'C':   
    cl.memLabel.setText(" ");cl.memValue=0.0;break;  
case 'R':   
    cl.displayLabel.setText(MyCalculator.getFormattedText(cl.memValue));break;  
case 'S':  
    cl.memValue=0.0;  
case '+':   
    cl.memValue+=Double.parseDouble(cl.displayLabel.getText());  
    if(cl.displayLabel.getText().equals("0") || cl.displayLabel.getText().equals("0.0")  )  
        cl.memLabel.setText(" ");  
    else   
        cl.memLabel.setText("M");     
    break;  
}//switch  
}//actionPerformed  
}//class  
  
/*****************************************/  
  
class MySpecialButton extends Button implements ActionListener  
{  
MyCalculator cl;  
  
MySpecialButton(int x,int y, int width,int height,String cap, MyCalculator clc)  
{  
super(cap);  
setBounds(x,y,width,height);  
this.cl=clc;  
this.cl.add(this);  
addActionListener(this);  
}  
//////////////////////  
static String backSpace(String s)  
{  
String Res="";  
for(int i=0; i<s.length()-1; i++) Res+=s.charAt(i);  
return Res;  
}  
  
//////////////////////////////////////////////////////////  
public void actionPerformed(ActionEvent ev)  
{  
String opText=((MySpecialButton)ev.getSource()).getLabel();  
//check for backspace button  
if(opText.equals("Backspc"))  
{  
String tempText=backSpace(cl.displayLabel.getText());  
if(tempText.equals(""))   
    cl.displayLabel.setText("0");  
else   
    cl.displayLabel.setText(tempText);  
return;  
}  
//check for "C" button i.e. Reset  
if(opText.equals("C"))   
{  
cl.number=0.0; cl.op=' '; cl.memValue=0.0;  
cl.memLabel.setText(" ");  
}  
  
//it must be CE button pressed  
cl.displayLabel.setText("0");cl.setClear=true;  
}//actionPerformed  
}//class  




