package demo.ui;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.annotation.PostConstruct;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.ImageRenderer;

import demo.ui.event.ReloadEntriesEvent;

@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View, ReloadEntriesEvent.ReloadEntriesListener {
    /*
     * This view is registered automatically based on the @SpringView
     * annotation. As it has an empty string as its view name, it will be shown
     * when navigating to the Homepage
     */
    public static final String VIEW_NAME = "";

    @PostConstruct
    void init() {
	addComponent(new Label("Welcome View"));
	VerticalLayout vl = new VerticalLayout();
	addComponent(summary(vl));
    }

    public VerticalLayout summary(VerticalLayout layout) {
	// BEGIN-EXAMPLE: component.grid.renderer.summary
	// BOOK: components.grid.renderer
	// Create a grid
	Grid grid = new Grid();
	grid.setCaption("My Grid");
	grid.setWidth("680px");
	grid.setHeight("380px");

	// Style image column and set row heights
	grid.setStyleName("gridwithpics128px");

	// Disable selecting items
	grid.setSelectionMode(SelectionMode.NONE);

	ButtonRenderer br = new ButtonRenderer(e -> Notification
		.show("Clicked " + grid.getContainerDataSource().getContainerProperty(e.getItemId(), "name")));

	// br.setIcon(new
	// ExternalResource("http://vaadin.com/image/user_male_portrait?img_id=44268&t=1251193981449"));
	Converter<String, String> cr = new Converter<String, String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String convertToModel(String value, Class<? extends String> targetType, Locale locale)
		    throws Converter.ConversionException {
		return "not implemented";
	    }

	    @Override
	    public String convertToPresentation(String value, Class<? extends String> targetType, Locale locale)
		    throws com.vaadin.data.util.converter.Converter.ConversionException {
		System.out.println("Button value is " + value);
		if (Boolean.parseBoolean(value)) {
		    return "Enable";
		} else {
		    return "Disable";
		}

	    }

	    @Override
	    public Class<String> getModelType() {
		return String.class;
	    }

	    @Override
	    public Class<String> getPresentationType() {
		return String.class;
	    }
	};
	// Define some columns
	grid.addColumn("picture", Resource.class).setRenderer(new ImageRenderer());
	grid.addColumn("name", String.class);
	grid.addColumn("born", Date.class);
	grid.addColumn("link", String.class);
	grid.addColumn("status", String.class).setRenderer(br, cr);

	Grid.Column bornColumn = grid.getColumn("born");
	bornColumn.setRenderer(new DateRenderer("%1$tB %1$te, %1$tY", Locale.ENGLISH));

	Grid.Column linkColumn = grid.getColumn("link");
	linkColumn.setRenderer(new HtmlRenderer(), new Converter<String, String>() {
	    private static final long serialVersionUID = 6394779294728581811L;

	    @Override
	    public String convertToModel(String value, Class<? extends String> targetType, Locale locale)
		    throws Converter.ConversionException {
		return "not implemented";
	    }

	    @Override
	    public String convertToPresentation(String value, Class<? extends String> targetType, Locale locale)
		    throws com.vaadin.data.util.converter.Converter.ConversionException {
		return "<a href='http://en.wikipedia.org/wiki/" + value + "' target='_top'>more info</a>";
	    }

	    @Override
	    public Class<String> getModelType() {
		return String.class;
	    }

	    @Override
	    public Class<String> getPresentationType() {
		return String.class;
	    }
	});

	// Add some data rows
	grid.addRow(new ThemeResource("img/copernicus-128px.jpg"), "Nicolaus Copernicus",
		new GregorianCalendar(1473, 2, 19).getTime(), "Nicolaus_Copernicus", "true");
	grid.addRow(new ThemeResource("img/galileo-128px.jpg"), "Galileo Galilei",
		new GregorianCalendar(1564, 2, 15).getTime(), "Galileo_Galilei", "false");
	grid.addRow(new ThemeResource("img/kepler-128px.jpg"), "Johannes Kepler",
		new GregorianCalendar(1571, 12, 27).getTime(), "Johannes_Kepler", "true");

	grid.setCellStyleGenerator(cell -> "picture".equals(cell.getPropertyId()) ? "imagecol" : null);

	layout.addComponent(grid);
	// END-EXAMPLE: component.grid.renderer.summary

	return layout;
    }

    @Override
    public void enter(ViewChangeEvent event) {
	// the view is constructed in the init() method()
    }

    @Override
    public void reloadEntries(ReloadEntriesEvent event) {
	// TODO Auto-generated method stub

    }
}