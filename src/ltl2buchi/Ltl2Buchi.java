package ltl2buchi;

import java.lang.*;
import java.util.*;
import java.util.regex.*;

/**
  * Description This class should generate a Buchi automata from a LTL formula,
                the result of the computation should be showed on the screen.
  *
  * @author	Kyller Costa Gorgônio
  * @version	0.1 - 24 april 2003
  */
public class Ltl2Buchi {

    public Vector NodeList;

    private Node init;
    private String f1;
    private String f2;
    /*
    private Node q;
    private Node q1;
    private Node q2;
    */

    private static final int ERRO = -1;
    private static final int CASE_1 = 1;
    private static final int CASE_2 = 2;
    private static final int CASE_3 = 3;
    private static final int CASE_4 = 4;
    private static final int CASE_5 = 5;
    private static final int CASE_6 = 6;

    public static void main(String[] args) throws EmptyFormulaException{

	try {
    		NegationNormalForm nnf = new NegationNormalForm();
		Ltl2Buchi automata = new Ltl2Buchi(nnf.LTL_NNF);

    		Vector a = automata.CreateGraph(nnf.LTL_NNF);
		Enumeration e = a.elements();
		while (e.hasMoreElements()) {

			Node n = (Node)e.nextElement();
			automata.print_node(n);
			
		}
	} catch (EmptyFormulaException e) {

		System.out.println("Ltl2Buchi.main() --> Unable to process" +
			"an empty ltl formula\n");
		throw e;
	}
    }

    public Ltl2Buchi(String fi) {

	init = new Node();
	NodeList = new Vector();
    }

    public Vector CreateGraph(String fi) {

	Node q = new Node();
	q.addObject(Integer.valueOf(Integer.toString(init.ID)), null, fi, null);
	Vector v = new Vector();
	// System.out.println("Ltl2Buchi.CreateGraph() - init ID - " + init.ID);
    	NodeList = Expand(q, v);
	return NodeList;
    }

    private Vector Expand(Node node, Vector listOfNodes) {

	System.out.print(". :: Processando novo Node :: .");
	print_node(node);

    	if (node.New.isEmpty()) { // node.New is empty

		int i = ERRO;

		if ((i = testNodeExists(node, listOfNodes)) != ERRO) { 

			((Node)listOfNodes.elementAt(i)).addVector
				(node.Incoming, null, null, null);
			return listOfNodes;
		} else {

			Node q = new Node();
			q.addObject(Integer.valueOf(Integer.toString(node.ID)), 
				null, null, null);
			q.addVector(null, null, node.Next, null);
			listOfNodes.addElement(node);
			listOfNodes = Expand(q, listOfNodes);
		}
	} else { // node.New is not empty

		// String fi = (String)node.New.remove(node.New.size() - 1);
		String fi = (String)node.New.remove(0);

		if (node.Old.contains(fi)) {

			listOfNodes = Expand(node, listOfNodes);
		} else {

			int ft = formulaType(fi);
			Node q1 = new Node();
			Node q2 = new Node();

			switch (ft) {

			    case CASE_1:

				q1.addVector(node.Incoming, node.Old, node.New, 
					node.Next);
				q1.addObject(null, fi, null, null);
				listOfNodes = Expand(q1, listOfNodes);
				break;
			    case CASE_2:

				q1.addVector(node.Incoming, node.Old, node.New, 
					node.Next);
				q1.addObject(null, fi, f1, fi);
				q2.addVector(node.Incoming, node.Old, node.New, 
					node.Next);
				q2.addObject(null, fi, f2, null);
				listOfNodes = Expand(q2, Expand(q1, listOfNodes));
				break;
			    case CASE_3:

				q1.addVector(node.Incoming, node.Old, node.New, 
					node.Next);
				q1.addObject(null, fi, f1, null);
				q1.addObject(null, null, f2, null);
				q2.addVector(node.Incoming, node.Old, node.New, 
					node.Next);
				q2.addObject(null, fi, f2, fi);
				listOfNodes = Expand(q2, Expand(q1, listOfNodes));
				break;
			    case CASE_4:
			    
				q1.addVector(node.Incoming, node.Old, node.New, 
					node.Next);
				q1.addObject(null, fi, f1, null);
				q2.addVector(node.Incoming, node.Old, node.New, 
					node.Next);
				q2.addObject(null, fi, f2, null);
				listOfNodes = Expand(q2, Expand(q1, listOfNodes));
				break;
			    case CASE_5:
				
				q1.addVector(node.Incoming, node.Old, node.New, 
					node.Next);
				q1.addObject(null, fi, f1, null);
				q1.addObject(null, null, f2, null);
				listOfNodes = Expand(q1, listOfNodes);
				break;
			    case CASE_6:
			    
				q1.addVector(node.Incoming, node.Old, node.New, 
					node.Next);
				q1.addObject(null, fi, null, f1);
				listOfNodes = Expand(q1, listOfNodes);
				break;
			    
			    default:

				System.out.println("Ltl2Buchi.Expand() --> " +
					"Syntax error on LTL formula\n");
				break;
			}
		}
	}
	
	return listOfNodes;
    }

    // TODO - Pensar em uma expressao regular que descreve a gramática
    private int formulaType(String formula) {

	// formula is a proposition, its negation, True or False
	String case1 = "\\p{Space}*True\\p{Space}*|" +
	 	       "\\p{Space}*False\\p{Space}*|" +
		       "\\p{Space}*\\p{Space}*\\p{Lower}[\\p{Lower}\\p{Digit}]*\\p{Space}*\\p{Space}*|" +
		       "\\p{Space}*Neg\\p{Space}*\\(\\p{Space}*\\p{Lower}[\\p{Lower}\\p{Digit}]*\\p{Space}*\\)\\p{Space}*";

	// formula is of the type formula1 U formula2
	String case2 = "\\p{Space}*U\\p{Space}*\\(.*\\)\\p{Space}*";

	// formula is of the type formula1 R formula2
	String case3 = "\\p{Space}*R\\p{Space}*\\(.*\\)\\p{Space}*";

	// formula is of the type formula1 OR formula2
	String case4 = "\\p{Space}*OR\\p{Space}*\\(.*\\)\\p{Space}*";

	// formula is of the type formula1 AND formula2
	String case5 = "\\p{Space}*AND\\p{Space}*\\(.*\\)\\p{Space}*";

	// formula is of the type X formula
	String case6 = "\\p{Space}*X\\p{Space}*\\(.*\\)\\p{Space}*";

	if (Pattern.matches(case1, formula)) {

		System.out.println("case1");
		return CASE_1;
	} else if (Pattern.matches(case2, formula)) {

		System.out.println("case2");
		break_formula(formula);
		return CASE_2;
	} else if (Pattern.matches(case3, formula)) {

		System.out.println("case3");
		break_formula(formula);
		return CASE_3;
	} else if (Pattern.matches(case4, formula)) {

		System.out.println("case4");
		break_formula(formula);
		return CASE_4;
	} else if (Pattern.matches(case5, formula)) {

		System.out.println("case5");
		break_formula(formula);
		return CASE_5;
	} else if (Pattern.matches(case6, formula)) {

		System.out.println("case6");
		take_formula(formula);
		return CASE_6;
	}

	return ERRO;
    }

    private void break_formula(String formula) {

	int begin = formula.indexOf('(');
	int end = formula.lastIndexOf(')');

	int next1 = begin; // para (
	int next2 = begin; // para ,

	while (next1 <= next2 && next1 > 0 && next2 > 0) {

		next1 = formula.indexOf('(', next1 + 1);
		next2 = formula.indexOf(',', next2 + 1);
	}

	f1 = formula.substring(begin + 1, next2);
	f2 = formula.substring(next2 + 1, end);
    }

    private void take_formula(String formula) {

	int begin = formula.indexOf('(');
	int end = formula.lastIndexOf(')');

	f1 = formula.substring(begin + 1, end);
    }

    private int testNodeExists(Node node, Vector listOfNodes) {

    	Enumeration e = listOfNodes.elements();
	int cont = 0;

	while (e.hasMoreElements()) {

		Node currentNode = (Node) e.nextElement();

		if (node.Old.equals(currentNode.Old) && 
		    node.Next.equals(currentNode.Next)) {

			return cont;
		}
		cont++;
	}

	return ERRO;
    }

    public void print_node(Node n) {

    	System.out.println("\nNode ID    : " + n.ID);
	
	Enumeration e2 = n.Incoming.elements();
	while (e2.hasMoreElements()) {

		System.out.println("  Incoming : " + e2.nextElement());
	}

	e2 = n.Old.elements();
	while (e2.hasMoreElements()) {

		System.out.println("  Old      : " + e2.nextElement());
	}

	e2 = n.New.elements();
	while (e2.hasMoreElements()) {

		System.out.println("  New      : " + e2.nextElement());
	}

	e2 = n.Next.elements();
	while (e2.hasMoreElements()) {

		System.out.println("  Next     : " + e2.nextElement());
	}
    }
}
