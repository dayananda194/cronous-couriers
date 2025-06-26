package org.syncron.cronouscouriers.entities;

import org.junit.jupiter.api.Test;
import org.syncron.cronouscouriers.enums.AssignmentStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssigmentTest {

    @Test
    void testAssigment(){

        Assignment assignment = new Assignment("Assignment-1","Offer-Letter","Dayananda");
        assertEquals("Assignment-1", assignment.getId());
        assertEquals("Offer-Letter",assignment.getPackageId());
        assertEquals("Dayananda",assignment.getRiderId());
        assertEquals(AssignmentStatus.ASSIGNED,assignment.getStatus());
    }

    @Test
    void testAssigmentToString(){

        Assignment assignment = new Assignment("Assignment-1","Offer-Letter","Dayananda");
        String assigmentToString = assignment.toString();
        assertTrue(assigmentToString.contains("Assignment-1"));
        assertTrue(assigmentToString.contains("Offer-Letter"));
        assertTrue(assigmentToString.contains("Dayananda"));
        assertTrue(assigmentToString.contains("ASSIGNED"));
    }
}
