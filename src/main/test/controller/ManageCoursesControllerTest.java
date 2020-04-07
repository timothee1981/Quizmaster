package controller;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//@Auteur: Mireille. Deze test heb ik gemaakt om de flow van het maken van een test in de vingers te krijgen

class ManageCoursesControllerTest {

    @Test
    void getallenOptellen() {
        ManageCoursesController testMethode = new ManageCoursesController();
        int result = testMethode.getallenOptellen(3,3);
        assertEquals(6, result);
    }
}