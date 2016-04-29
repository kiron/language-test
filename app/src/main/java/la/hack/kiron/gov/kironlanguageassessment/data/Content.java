package la.hack.kiron.gov.kironlanguageassessment.data;

import android.media.Image;

/**
 * Question content.
 */
public interface Content {

    /**
     * Question that should be displayed (usually a text, may use HTML)
     */
    String getContent();

    /**
     * Determines whether a picture is available for this question.
     */
    boolean hasPicture();

    /**
     * Question picture
     * @return Image (if present)
     */
    Image getPicture();
}
