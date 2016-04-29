package la.hack.kiron.gov.kironlanguageassessment.data;

/**
 * Answer representation
 */
public interface Answer {

    /**
     * Answer Id (unique among all answers)
     */
    int getId();

    /**
     * Answer that should be displayed (usually a text, may use HTML)
     */
    String getContent();
}
