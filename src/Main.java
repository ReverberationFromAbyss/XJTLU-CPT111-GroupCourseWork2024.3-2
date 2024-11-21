public class Main {
public static void main(String[] args) {

  var ins = Logical.getInstance()
                   .RegisterStartupFunctionHook(Logical::LoadRequiredInfo)
                   .RegisterAfterFunctionHook(Logical::SaveInfo)
                   .StartupFunctionCall()
                   .AfterFunctionCall();

}

}
