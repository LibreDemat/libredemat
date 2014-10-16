package org.libredemat.generator.common;

/**
 * @author jsb@zenexity.fr
 *
 */
public class CustomStep extends Step {

    private String name;
    private boolean required = true;
    private boolean displayNotInValidation = false;
    private boolean displayNotInPDF = false;


    public CustomStep(String index, String name, String displayNotInValidation, String displayNotInPDF, String required) {

        super(index);
        try {
            CommonStep.Ref.valueOf(name);
            throw new RuntimeException("Step() - attempt to use reserved name "
                 + name + " for a custom step");
        } catch (IllegalArgumentException e) {
            // OK, the name isn't reserved
        }
        this.name = name;
        if (required != null)
            this.required = Boolean.valueOf(required);
        if (displayNotInValidation != null)
            this.displayNotInValidation = Boolean.valueOf(displayNotInValidation);
        if (displayNotInPDF != null)
            this.displayNotInPDF = Boolean.valueOf(displayNotInPDF);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public boolean isDisplayNotInValidation() {
        return displayNotInValidation;
    }

    public void setDisplayNotInValidation(boolean displayNotInValidation) {
        this.displayNotInValidation = displayNotInValidation;
    }

    @Override
    public boolean isDisplayNotInPDF() {
        return displayNotInPDF;
    }

    public void setDisplayNotInPDF(boolean displayNotInPDF) {
        this.displayNotInPDF = displayNotInPDF;
    }

}
