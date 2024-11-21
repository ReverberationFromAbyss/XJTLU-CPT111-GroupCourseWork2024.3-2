package edu.cn.xjtlu.cpt111.cw3.cw3ljfx;

public class Main {
public static void main(String[] args) {

  var ins = Logical.getInstance()
                   .RegisterStartupFunctionHook(Logical::LoadRequiredInfo)
                   .RegisterAfterFunctionHook(Logical::SaveInfo)
                   .StartupFunctionCall()
                   .AfterFunctionCall();

}
}
