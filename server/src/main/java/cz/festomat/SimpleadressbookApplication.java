package com.example.simpleadressbook;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.vaadin.Application;

import com.vaadin.ui.*;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import fi.abo.GAEContainer.impl.Cache.Factory.CacheFactory;
import fi.abo.GAEContainer.impl.Cache.Factory.LocalMemoryCacheConfig;
import fi.abo.GAEContainer.impl.Cache.Factory.LocalMemoryCacheConfig.RemoveStrategy;
import fi.abo.GAEContainer.impl.Cache.Factory.MemCacheConfig;

public class SimpleadressbookApplication extends Application {

    private static String[] fields = { "firstname", "lastname", "Company",
            "Mobile Phone", "Work Phone", "Home Phone", "Work Email",
            "Home Email", "Street", "Zip", "city", "State", "country" };
    private static String[] visibleCols = new String[] { "firstname",
            "lastname", "city","country" };

    private Table contactList = new Table();
    private Form contactEditor = new Form();
    private HorizontalLayout bottomLeftCorner = new HorizontalLayout();
    private Button contactRemovalButton;
    
    
    //set up the cache configs
    
    static final LocalMemoryCacheConfig localMemoryCacheConfig = new LocalMemoryCacheConfig.Builder()
    .withCacheFilteredIndexes(true)
    .withCacheFilteredSizes(true)
    .withIndexCapacity(50)
    .withIndexLifeTime(60)
    .withItemCapacity(500)
    .withItemLifeTime(60)
    .withLineSize(25)
    .withRemoveStrategy(RemoveStrategy.LRU)
    .withSizeCapacity(10)
    .withSizeLifeTime(60).Build();
    
    static final MemCacheConfig memCacheConfig = new MemCacheConfig.Builder()
    .withCacheFilteredIndexes(true)
    .withIndexLifeTime(500)
    .withItemLifeTime(500)
    .withLineSize(50).Build();
    
    //create the GAEContainer with the caches and property write trough and no optimistic locking
    private fi.abo.GAEContainer.impl.GAEContainer addressBookData = new fi.abo.GAEContainer.impl.GAEContainer("People", true, false, CacheFactory.getCache(localMemoryCacheConfig), CacheFactory.getCache(memCacheConfig));
   
    @Override
    public void init() {
    	//createDummyData();
    	
    	for (String p : fields) {
            addressBookData.addContainerProperty(p, String.class, " ");
        }
        initLayout();
        initContactAddRemoveButtons();
        initAddressList();
        initFilteringControls();
   
        
    }

    private void initLayout() {
        SplitPanel splitPanel = new SplitPanel(
                SplitPanel.ORIENTATION_HORIZONTAL);
        //SplitPanel p = new SplitPanel(SplitPanel.ORIENTATION_VERTICAL);
        //p.addComponent(splitPanel);
        setMainWindow(new Window("Address Book", splitPanel));
        VerticalLayout left = new VerticalLayout();
        left.setSizeFull();
        
        left.addComponent(contactList);
        contactList.setSizeFull();
        left.setExpandRatio(contactList, 1);
        splitPanel.addComponent(left);
        VerticalLayout p = new VerticalLayout();
        p.addComponent(contactEditor);
        
        splitPanel.addComponent(p);
        Label label = new Label("<br><br>This is a Vaadin application running inside App Engine using the GAEContainer as a datasource for a table." +
        		" As App Engine is very slow when starting new instances please click around a bit to get a accurate view of the performance. If you experience" +
		" response times over 1 sec a is most likely new instance was created.", Label.CONTENT_RAW);
        label.setWidth("400px");
        
        p.addComponent(label);
        //p.setComponentAlignment(label, );
        p.setComponentAlignment(label,Alignment.MIDDLE_CENTER);
        contactEditor.setSizeFull();
        contactEditor.getLayout().setMargin(true);
        contactEditor.setImmediate(true);
        bottomLeftCorner.setWidth("100%");
        left.addComponent(bottomLeftCorner);
    }

    private void initContactAddRemoveButtons() {
        // New item button
        bottomLeftCorner.addComponent(new Button("+",
                new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        Object id = contactList.addItem();
                        contactList.setValue(id);
                    }
                }));

        // Remove item button
        contactRemovalButton = new Button("-", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                contactList.removeItem(contactList.getValue());
                contactList.select(null);
            }
        });
        contactRemovalButton.setVisible(false);
        bottomLeftCorner.addComponent(contactRemovalButton);
    }

    private String[] initAddressList() {
        contactList.setContainerDataSource(addressBookData);
        contactList.setVisibleColumns(visibleCols);
        contactList.setSelectable(true);
        contactList.setImmediate(true);
        contactList.addListener(new Property.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Object id = contactList.getValue();
                contactEditor.setItemDataSource(id == null ? null : contactList
                        .getItem(id));
                contactRemovalButton.setVisible(id != null);
            }
        });
        return visibleCols;
    }

    private void initFilteringControls() {
        for (final String pn : visibleCols) {
            final TextField sf = new TextField();
            bottomLeftCorner.addComponent(sf);
            sf.setWidth("100%");
            sf.setInputPrompt(pn);
            sf.setImmediate(true);
            bottomLeftCorner.setExpandRatio(sf, 1);
            sf.addListener(new Property.ValueChangeListener() {
                public void valueChange(ValueChangeEvent event) {
                	//the GAEContainer does not use the vaadin container interface for filtering
                    addressBookData.removeFilters(pn);// removeContainerFilters(pn);
                    if (sf.toString().length() > 0 && !pn.equals(sf.toString())) {
                        addressBookData.addFilter(pn, FilterOperator.EQUAL, sf.toString()); //addContainerFilter(pn, sf.toString(), true, false);
                    }
                    getMainWindow().showNotification(
                            "" + addressBookData.size() + " matches found");
                }
            });
        }
    }

    private static void createDummyData() {

        String[] fnames = { "Peter", "Alice", "Joshua", "Mike", "Olivia",
                "Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene",
                "Lisa", "Marge" };
        String[] lnames = { "Smith", "Gordon", "Simpson", "Brown", "Clavel",
                "Simons", "Verne", "Scott", "Allison", "Gates", "Rowling",
                "Barks", "Ross", "Schneider", "Tate" };

        fi.abo.GAEContainer.impl.GAEContainer ic = new fi.abo.GAEContainer.impl.GAEContainer("adress", true, false);

        for (String p : fields) {
            ic.addContainerProperty(p, String.class, "unset");
        }

        for (int i = 0; i < 10; i++) {
            Object id = ic.addItem();
            ic.getContainerProperty(id, "First Name").setValue(
                    fnames[(int) (fnames.length * Math.random())]);
            ic.getContainerProperty(id, "Last Name").setValue(
                    lnames[(int) (lnames.length * Math.random())]);
            System.out.println(ic.getContainerProperty(id, "Last Name").getValue());
        }
        
  

       
    }

}
