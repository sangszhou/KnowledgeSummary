package annotation.nameCheckProcessor;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;

/**
 * Created by xinszhou on 7/12/16.
 */
public class NameCheck extends ElementScanner8<Void, Void> {

    private Messager messager;

    public NameCheck(Messager messager) {
        this.messager = messager;
    }

    //what's the relation between scan and visitType?
    public void checkNames(Element ele) {
        scan(ele);
    }

    @Override
    public Void visitType(TypeElement e, Void p) {
        scan(e, p);
        messager.printMessage(Diagnostic.Kind.WARNING, "this is a class definition");
        super.visitType(e, p);
        return null;
    }

    @Override
    public Void visitExecutable(ExecutableElement e, Void p) {
        scan(e, p);
        messager.printMessage(Diagnostic.Kind.NOTE, "this a a method definition");
        super.visitExecutable(e, p);
        return null;
    }

    @Override
    public Void visitVariable(VariableElement e, Void p) {
        scan(e, p);
        messager.printMessage(Diagnostic.Kind.NOTE, "a variable definition");
        super.visitVariable(e, p);
        return null;
    }


}
