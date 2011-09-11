package ltl2buchi;

/** 
  * This exception occurs when someone try to pass an empty formula to
  * ltl2buchi algorithm
  *
  * @author 	Kyller Costa Gorgônio
  * @version 	0.1 - 24 april 2003
  */
public class EmptyFormulaException extends Exception {

    public EmptyFormulaException() {
    
    	super();
    }
    
    public EmptyFormulaException(String msg) {
    
    	super(msg);
    }
}
