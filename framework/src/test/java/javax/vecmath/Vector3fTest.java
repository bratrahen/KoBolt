package javax.vecmath;

import static org.junit.Assert.*;
import org.junit.Test;

public class Vector3fTest {

    @Test
    public void substract(){
        Vector3f attackerPosition = new Vector3f(11, 22, 33);
        Vector3f defenderPosition = new Vector3f(100, 200, 300);

        Vector3f attackVector = new Vector3f(defenderPosition);
        attackVector.sub(attackerPosition);

        assertEquals(new Vector3f(89, 178, 267), attackVector);
    }
}
