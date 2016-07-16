package classLoader;


import org.gjt.jclasslib.io.ClassFileWriter;
import org.gjt.jclasslib.structures.ClassFile;
import org.gjt.jclasslib.structures.Constant;
import org.gjt.jclasslib.structures.constants.ConstantUtf8Info;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by xinszhou on 16/7/6.
 *
 * 这个 class 有两个需求, 首先是 maven 加载 jclasslib 包, 其次是添加 kotlin 依赖
 * jclasslib 用 maven 加载参考
 * [[https://devcenter.heroku.com/articles/local-maven-dependencies#deploy-the-artifact-into-the-repo]
 * kotlin 这个 jar 包需要 Ktolin 的一个 exception (至少一个)
 *
 */
public class HackClassFile {

    public static void main(String[] args) throws Exception {
        String classFilePath = System.getProperty("user.dir") + "/JVM/src/main/resources/HelloWord.class";
        String destFilePath = System.getProperty("user.dir") + "/JVM/target/classes/HelloWorld.class";
        System.out.println(classFilePath);
        FileInputStream fis = new FileInputStream(classFilePath);

        ClassFile cf = new ClassFile();
        DataInput di = new DataInputStream(fis);
        cf.read(di);

        Constant[] infos = cf.getConstantPool();

        int count = infos.length;

        for (int i = 0; i < count; i++) {
            if (infos[i] != null) {
                System.out.print(i);
                System.out.print(" = ");
                System.out.print(infos[i].getVerbose());
                System.out.print(" = ");
                System.out.println(infos[i].getVerbose());

                if(i == 23){
                    ConstantUtf8Info uInfo = (ConstantUtf8Info)infos[i];
//                 uInfo.setBytes("芝麻不开门!".getBytes());
                    uInfo.setString("Pay back");
                    infos[i]=uInfo;
                }
            }
        }

        cf.setConstantPool(infos);
        fis.close();

        File newFile = new File(System.getProperty("user.dir") + "/JVM/src/main/resources/HelloWord.class");

        ClassFileWriter.writeToFile(newFile, cf);

    }
}
