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
 *     Richard Malek
 *     - Initial implementation
 */
package org.seage.problem.tsp.antcolony;

import org.seage.metaheuristic.antcolony.Edge;
import org.seage.metaheuristic.antcolony.Graph;
import org.seage.metaheuristic.antcolony.Node;
import org.seage.problem.tsp.City;

/**
 *
 * @author Zagy
 */
public class TspGraph extends Graph {

    public TspGraph(City[] cities, double locEvaporCoeff, double defaultPheromone) {
        super(locEvaporCoeff);
        for (int id = 0; id < cities.length; id++) {
            _nodeList.add(new Node(id));
        }

        fillEdgeMap(cities);
        setDefaultPheromone(defaultPheromone);
    }

    /**
     * Edge length calculating
     * @param start - Starting node
     * @param end - Terminate node
     * @param cities - Readed cities
     * @return - Euclide edge length
     */
    private double calculateEdgeLength(Node start, Node end, City[] cities) {
        double _dX = (cities[start.getId()].X - cities[end.getId()].X);
        double _dY = (cities[start.getId()].Y - cities[end.getId()].Y);
        return Math.sqrt(_dX * _dX + _dY * _dY);
    }

    /**
     * List of graph edges filling
     */
    private void fillEdgeMap(City[] cities) {
        Edge helpEdge;
        boolean same = false;
        for (Node i : _nodeList) {
            for (Node j : _nodeList) {
                if (!i.equals(j)) {
                    Edge makedEdge = new Edge(i, j);
                    makedEdge.setEdgePrice(calculateEdgeLength(i, j, cities));
                    for (Edge k : _edgeList) {
                        helpEdge = k;
                        if (helpEdge.getNode1().equals(j) && helpEdge.getNode2().equals(i)) {
                            same = true;
                        }
                    }
                    if (!same) {
                        _edgeList.add(makedEdge);
                    }
                }
                same = false;
            }
        }
        for (Node i : _nodeList) {
            for (Edge j : _edgeList) {
                if (j.getNode1().equals(i) || j.getNode2().equals(i)) {
                    i.addConnection(j);
                }
            }
        }
    }

    @Override
    public void printPheromone() {
        for (Node n : _nodeList) {
            System.out.println(n.getId());
            for (Edge e : n.getConnectionMap()) {
                System.out.printf("\t%3.3f\t%3.5f\n", e.getEdgePrice(), e.getLocalPheromone());
            }
        }
    }
}
