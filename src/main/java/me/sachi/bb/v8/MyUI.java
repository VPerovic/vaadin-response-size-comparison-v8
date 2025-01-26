package me.sachi.bb.v8;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be
 * overridden to add component to the user interface and initialize
 * non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    private int numOfPersons = 40;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setSizeFull();
        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        Grid<Person> grid = new Grid<>();
        grid.setSizeFull();
        grid.setBodyRowHeight(100);
        grid.addComponentColumn(p->createCard(p));

        grid.setItems(createPersons());

        Button refreshButton = new Button("Refresh grid", click ->{
            grid.setItems(createPersons());
        });

        layout.addComponent(refreshButton);
        layout.addComponent(grid);
        layout.setExpandRatio(grid, 100);
        setContent(layout);
    }

    private List<Person> createPersons() {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < numOfPersons; i++) {
            persons.add(createPerson("https://randomuser.me/api/portraits/men/" + (i % 99) + ".jpg", i + " John Smith",
                    "May 8",
                    "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                    "1K", "500", "20"));
        }
        return persons;
    }

    private HorizontalLayout createCard(Person person) {
        HorizontalLayout card = new HorizontalLayout();
        card.addStyleName("card");
        card.setSpacing(false);

        Image image = new Image();
        image.addStyleName("img");
        image.setSource(new ExternalResource(person.getImage()));
        VerticalLayout description = new VerticalLayout();
        description.addStyleName("description");
        description.setSpacing(false);
        description.setMargin(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("header");
        header.setSpacing(false);

        Label name = new Label(person.getName());
        name.addStyleName("name");
        Label date = new Label(person.getDate());
        date.addStyleName("date");
        header.addComponents(name, date);

        Label post = new Label(person.getPost());
        post.addStyleName("post");

        HorizontalLayout actions = new HorizontalLayout();
        actions.addStyleName("actions");
        actions.setSpacing(false);

        Label likeIcon = new Label();
        likeIcon.setIcon(VaadinIcons.HEART);
        likeIcon.addStyleName("icon");
        Label likes = new Label(person.getLikes());
        likes.addStyleName("likes");
        Label commentIcon = new Label();
        commentIcon.setIcon(VaadinIcons.COMMENT);
        commentIcon.addStyleName("icon");
        Label comments = new Label(person.getComments());
        comments.addStyleName("comments");
        Label shareIcon = new Label();
        shareIcon.setIcon(VaadinIcons.CONNECT);
        shareIcon.addStyleName("icon");
        Label shares = new Label(person.getShares());
        shares.addStyleName("shares");

        actions.addComponents(likeIcon, likes, commentIcon, comments, shareIcon, shares);

        description.addComponents(header, post, actions);
        card.addComponents(image, description);
        return card;
    }

    private static Person createPerson(String image, String name, String date, String post, String likes,
            String comments, String shares) {
        Person p = new Person();
        p.setImage(image);
        p.setName(name);
        p.setDate(date);
        p.setPost(post);
        p.setLikes(likes);
        p.setComments(comments);
        p.setShares(shares);

        return p;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
