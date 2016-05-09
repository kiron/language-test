package ngo.kiron.language_assessment.app.data;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Question content.
 */
public interface Content extends Serializable {

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
    Bitmap getPicture();
}
