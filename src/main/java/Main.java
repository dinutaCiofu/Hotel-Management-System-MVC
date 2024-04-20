import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.FirstPageView;

public class Main {

    public static void main(final String[] args)  {
        FirstPageView firstPageView = new FirstPageView();
        GUIFrameSinglePointAccess.changePanel(firstPageView.getMainPanel(), "First page");
    }
}