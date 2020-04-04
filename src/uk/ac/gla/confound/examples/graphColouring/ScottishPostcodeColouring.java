package uk.ac.gla.confound.examples.graphColouring;

import uk.ac.gla.confound.problem.Problem;

public class ScottishPostcodeColouring extends Problem {
    public ScottishPostcodeColouring() {
        super(16); // There are 16 postcode areas in scotland
        variables[1].setName("IV");
        variables[2].setName("KW");
        variables[3].setName("AB");
        variables[4].setName("DD");
        variables[5].setName("KY");
        variables[6].setName("PH");
        variables[7].setName("PA");
        variables[8].setName("HS");
        variables[9].setName("G");
        variables[10].setName("TD");
        variables[11].setName("EH");
        variables[12].setName("DG");
        variables[13].setName("KA");
        variables[14].setName("ML");
        variables[15].setName("FK");
        variables[16].setName("ZE");
    }
}
