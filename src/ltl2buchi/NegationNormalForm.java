package ltl2buchi;

import java.io.*;
import java.lang.*;
import java.util.*;

/**
  * Description This class reads the file with the LTL formulas and transforms
                it to the "negation normal form"
  *
  * @author	Kyller Costa Gorgônio
  * @version	0.1 - 24 april 2003
  * @created	0.1 - 24 april 2003
  */
public class NegationNormalForm {

    public String LTL = "";
    public String LTL_NNF = "";

    private static String formulas_file = "formulas.ltl";

    public NegationNormalForm() throws EmptyFormulaException {

	this.readFormulas();
	this.LTL_NNF = this.LTL;
    	
	if (this.LTL_NNF.equals("")) {

		throw new EmptyFormulaException("NegationNormalForm." +
			"NegationNormalForm() --> Trying to process an " + 
			"empty ltl formula\n");
	}
    }
										
    /**
      * Description	This method is used to read a (set of) ltl formulas
      			from a configuration file
      *
      * @author		Kyller Costa Gorgônio
      * @version	0.1 - 24 april 2003
      * @created	0.1 - 24 april 2003
      * @parameters	void
      * @return		void
      */
    private void readFormulas() {

	try {
    		InputStream is = getClass().getResourceAsStream(formulas_file);
    		Properties props = new Properties();
		
		props.load(is);
		this.LTL = props.getProperty("ltl");
		
	} catch (Exception excecao) {
		
		System.err.println("NegationNormalForm.readFormulas() " +
			"--> Unable to read the file " + formulas_file +
			" with the LTL formulas specification.\n\n");
	}
    }

    /**
      * Description	This method is used to read a (set of) ltl formulas
      			from a configuration file
      *
      * @author		Kyller Costa Gorgônio
      * @version	0.1 - 24 april 2003
      * @created	0.1 - 24 april 2003
      * @parameters	void
      * @return		void
      */
    private void toNNF() {

    	/* 
	
	TODO	For a while I will assume that formulas passed to this program
		are already in NNF.
	
	*/
		

    	/* definicao de regras de transformacao

		* Fp		--> True U p
		* Gp		--> False U p
		* A ==> B	--> ¬A v B
		* ¬(pUq)	--> (¬p) R (¬q)
		* ¬(pRq)	--> (¬p) U (¬q)
		* ¬Xp		--> X¬p

	*/

	/*
	while (st.hasMoreTokens()) {

		System.out.println("\n\t--> " + st.nextToken());
	}
	*/
    }
}
