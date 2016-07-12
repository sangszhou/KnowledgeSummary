package annotation.nameCheckProcessor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;


/**
 * Created by xinszhou on 7/12/16.
 */
@SupportedAnnotationTypes("*")
public class NameCheckerProcessor extends AbstractProcessor {

    private NameCheck nameChecker;

    @Override
    public void init(ProcessingEnvironment env) {
        super.init(env);
        nameChecker = new NameCheck(env.getMessager());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        if(!roundEnvironment.processingOver()) {
            for(Element element: roundEnvironment.getRootElements()) {
                nameChecker.checkNames(element);
            }
        }

        return false;
    }
}
