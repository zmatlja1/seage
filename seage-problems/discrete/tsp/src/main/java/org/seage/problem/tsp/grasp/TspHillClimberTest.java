/*******************************************************************************
 * Copyright (c) 2009 Richard Malek and SEAGE contributors

 * This file is part of SEAGE.

 * SEAGE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * SEAGE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with SEAGE. If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *     Martin Zaloga
 *     - Initial implementation
 */
package org.seage.problem.tsp.grasp;

import java.io.FileInputStream;
import org.seage.problem.tsp.CityProvider;
import org.seage.problem.tsp.City;
import org.seage.metaheuristic.grasp.HillClimber;
import org.seage.problem.tsp.Visualizer;

/**
 *
 * @author Martin Zaloga
 * @deprecated Replaced by TspProblemSolver
 */
public class TspHillClimberTest {

    /**
     * _cities - List of a loaded cities
     * _tour - Index list of cities that make up the path
     * _hc - Object containing hill climber algorithm
     */
    private City[] _cities;
    private Integer[] _tour;
    private HillClimber _hc;

    /**
     * The main trigger method
     * @param args - the argument is the path to the data
     */
    public static void main(String[] args) {
        try {
            new TspHillClimberTest().run(args[0], "greedy", 500, 500);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Function for the run the program
     * @param path - Path where is fyle of data the cities
     * @param clasic - Switching between the classical and improved algorithm Hill-Climber
     * @param switcher - Switching between the Geedy and Random initial solution
     * @param restarts - Numer of repeat optimalizations algorithm
     * @param iteration - Number of iteration algorthm
     */
    public void run(String path, String switcher, int restarts, int iteration) throws Exception {
        _cities = CityProvider.readCities(new FileInputStream(path));
        System.out.println("Loading cities from path: " + path);
        System.out.println("Number of cities: " + _cities.length);

        _hc = new HillClimber(new TspObjectiveFunction(_cities), new TspMoveManager(), new TSPSolutionGenerator(switcher, _cities), iteration);
        _hc.startRestartedSearching(restarts);
        TspSolution bestSol = (TspSolution) _hc.getBestSolution();
        _tour = bestSol.getTour();

        Visualizer.instance().createGraph(_cities, _tour, "tsphcgraph.png", 600, 400);
        System.out.println("length: " + bestSol.getObjectiveValue());
    }
}
