package ch.ffhs.savvy_spring.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("")
public class HelloWorld extends Div{
    public HelloWorld() {
        Div text = new Div(new Text("Hello World! Hot Reload Test 2.0 Test"));
        Div text2 = new Div(new Text("Hello World!"));
        add(text, text2);
    }

}
