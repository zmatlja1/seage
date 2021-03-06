/*******************************************************************************
 * Copyright (c) 2009 Richard Malek and SEAGE contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://seage.sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Richard Malek
 *     - Initial implementation
 */
package org.seage.problem.qap.tabusearch;

import org.seage.metaheuristic.tabusearch.*;

/**
 *
 * @author Karel Durkota
 */
public class QapSolution extends SolutionAdapter
{
    protected Integer[] _assign;
    
    public QapSolution(){} // Appease clone()

    public QapSolution(Double[][][] customers)
    {
        // Crudely initialize solution
        _assign = new Integer[ customers.length ];
        for( int i = 0; i < customers.length; i++ )
            _assign[i] = i;
    }   // end constructor
    
    public QapSolution(Integer[] assign){
        _assign = assign;
    }
    
    public Object clone()
    {
		QapSolution copy = (QapSolution)super.clone();
		copy._assign = (Integer[])this._assign.clone();
        return copy;
    }   // end clone

    public Integer[] getAssign()
    {
            return _assign;
    }

    public void setAssign(Integer[] assign)
    {
            _assign = assign;
    }
    
 public String toString()
    {
        StringBuffer s = new StringBuffer();

        s.append("[");
        for(int i=_assign.length-1;i>=1;i--){
            s.append((_assign[i]+1));
            s.append(",");
        }
        s.append((_assign[_assign[0]]+1)+"]");

        return s.toString();
    }   // end toString
    
}   // end class MySolution
