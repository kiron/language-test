package gov.kiron.android.la.data;

import java.io.Serializable;

/**
 * Answer representation
 */
public interface Answer extends Serializable {

    /**
     * Answer Id (unique among all answers)
     */
    int getId();

    /**
     * Answer that should be displayed (usually a text, may use HTML)
     */
    String getContent();
}
