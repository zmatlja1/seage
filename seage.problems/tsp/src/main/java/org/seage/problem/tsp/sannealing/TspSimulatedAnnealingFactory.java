/*******************************************************************************
 * Copyright (c) 2009 Richard Malek and SEAGE contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://seage.sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Jan Zmatlik
 *     - Initial implementation
 */
package org.seage.problem.tsp.sannealing;

import org.seage.aal.IAlgorithmAdapter;
import org.seage.aal.IAlgorithmFactory;
import org.seage.aal.sannealing.SimulatedAnnealingAdapter;
import org.seage.data.DataNode;
import org.seage.metaheuristic.sannealing.Solution;
import org.seage.problem.tsp.City;

/**
 *
 * @author Jan Zmatlik
 */
public class TspSimulatedAnnealingFactory implements IAlgorithmFactory
{
    //private DataNode _algParams;
    private City[] _cities;

    public TspSimulatedAnnealingFactory(DataNode params, City[] cities)
    {
        //_algParams = params;
        _cities = cities;
    }

    public IAlgorithmAdapter createAlgorithm() throws Exception
    {
        IAlgorithmAdapter algorithm;

        algorithm = new SimulatedAnnealingAdapter((Solution) new TspSolution( _cities , Solution.SolutionType.GREEDY ),
                new TspObjectiveFunction(),
                new TspMoveManager(), false, "")
        {
            public void solutionsFromPhenotype(Object[][] source) throws Exception 
            {
                TspSolution initialSolution = (TspSolution)getInitialSolution();
                Integer[] tour = initialSolution.getTour();

                for(int i = 0; i < tour.length; i++)
                    tour[i] = (Integer)source[0][i];
            }

            public Object[][] solutionsToPhenotype() throws Exception
            {
                Integer[] tour = ((TspSolution)getInitialSolution()).getTour();
                Object[][] source = new Object[1][ tour.length ];

                source[0] = new Integer[ tour.length ];
                for(int i = 0; i < tour.length; i++)
                    source[0][i] = tour[i];

                return source;
            }

        };

        return algorithm;
    }

}
