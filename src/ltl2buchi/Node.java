package ltl2buchi;

import java.lang.*;
import java.util.*;

/**
  * Description This class describes the data structure of a Node
  *
  * @author	Kyller Costa Gorgônio
  * @version	0.1 - 24 april 2003
  */
public class Node {

    public int ID;
    public Vector Incoming;
    public Vector Old;
    public Vector New;
    public Vector Next;

    public static int nextID= 1;
    public static int init = 0;

    public Node() {

    	this.ID = nextID++;
	Incoming = new Vector();
	Old = new Vector();
	New = new Vector();
	Next = new Vector();
    }

    public void addVector(Vector vinc, Vector vold, Vector vnew, Vector vnext) {

	if (vinc != null) {
	
		Enumeration e = vinc.elements();
		while (e.hasMoreElements()) {
	
			Object o = e.nextElement();
    			this.Incoming.addElement(o);
		}
	}

	if (vold != null) {
	
		Enumeration e = vold.elements();
		while (e.hasMoreElements()) {
	
			Object o = e.nextElement();
    			this.Old.addElement(o);
		}
	}


	if (vnew != null) {
	
		Enumeration e = vnew.elements();
		while (e.hasMoreElements()) {
	
			Object o = e.nextElement();
    			this.New.addElement(o);
		}
	}


	if (vnext != null) {
	
		Enumeration e = vnext.elements();
		while (e.hasMoreElements()) {
	
			Object o = e.nextElement();
    			this.Next.addElement(o);
		}
	}
    }

    public void addObject(Object oinc, Object oold, Object onew, Object onext) {

    	if (oinc != null) {

    		this.Incoming.addElement(oinc);
	}

    	if (oold != null) {
    		
		this.Old.addElement(oold);
	}

    	if (onew != null) {
    		
		this.New.addElement(onew);
	}

    	if (onext != null) {
	
    		this.Next.addElement(onext);
	}
    }

    public int getID() {

    	return(this.ID);
    }

    public Vector getIncoming() {

    	return(this.Incoming);
    }

    public Vector getOld() {

    	return(this.Old);
    }

    public Vector getNew() {

    	return(this.New);
    }

    public Vector getNext() {

    	return(this.Next);
    }

    public void clearIncoming() {

    	this.Incoming.clear();
    }

    public void clearOld() {

    	this.Old.clear();
    }

    public void clearNew() {

    	this.New.clear();
    }

    public void clearNext() {

    	this.Next.clear();
    }

    public void clearAll() {

    	this.clearIncoming();
	this.clearOld();
	this.clearNew();
	this.clearNext();
    }
}
