package cz.festomat.server;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import fi.abo.GAEContainer.impl.Cache.Factory.LocalMemoryCacheConfig;
import fi.abo.GAEContainer.impl.Cache.Factory.LocalMemoryCacheConfig.RemoveStrategy;
import fi.abo.GAEContainer.impl.Cache.Factory.MemCacheConfig;

public class Admin extends Application {

	private static String[]								fields					= { "jmeno", "adresa", "lng", "lat",
			"zacatek", "konec", "popis"										};
	private static String[]								visibleCols				= new String[] { "jmeno", "adresa",
			"lng", "lat"														};

	private final Table									contactList				= new Table();
	private final Form									contactEditor			= new Form();
	private final HorizontalLayout						bottomLeftCorner		= new HorizontalLayout();
	private Button										contactRemovalButton;

	// set up the cache configs

	static final LocalMemoryCacheConfig					localMemoryCacheConfig	= new LocalMemoryCacheConfig.Builder()
																						.withCacheFilteredIndexes(true)
																						.withCacheFilteredSizes(true)
																						.withIndexCapacity(50)
																						.withIndexLifeTime(60)
																						.withItemCapacity(500)
																						.withItemLifeTime(60)
																						.withLineSize(25)
																						.withRemoveStrategy(
																								RemoveStrategy.LRU)
																						.withSizeCapacity(10)
																						.withSizeLifeTime(60).Build();

	static final MemCacheConfig							memCacheConfig			= new MemCacheConfig.Builder()
																						.withCacheFilteredIndexes(true)
																						.withIndexLifeTime(500)
																						.withItemLifeTime(500)
																						.withLineSize(50).Build();

	// create the GAEContainer with the caches and property write trough and no optimistic locking
	private final fi.abo.GAEContainer.impl.GAEContainer	addressBookData			= new fi.abo.GAEContainer.impl.GAEContainer(
																						"Festival", true, false);

	@Override
	public void init() {
		// createDummyData();

		for (String p : fields) {
			addressBookData.addContainerProperty(p, String.class, " ");
		}
		initLayout();
		initContactAddRemoveButtons();
		initAddressList();
		initFilteringControls();

	}

	private void initLayout() {
		SplitPanel splitPanel = new SplitPanel(SplitPanel.ORIENTATION_HORIZONTAL);
		// SplitPanel p = new SplitPanel(SplitPanel.ORIENTATION_VERTICAL);
		// p.addComponent(splitPanel);
		setMainWindow(new Window("Festivaly", splitPanel));
		VerticalLayout left = new VerticalLayout();
		left.setSizeFull();

		left.addComponent(contactList);
		contactList.setSizeFull();
		left.setExpandRatio(contactList, 1);
		splitPanel.addComponent(left);
		VerticalLayout p = new VerticalLayout();
		p.addComponent(contactEditor);

		splitPanel.addComponent(p);

		contactEditor.setSizeFull();
		contactEditor.getLayout().setMargin(true);
		contactEditor.setImmediate(true);
		bottomLeftCorner.setWidth("100%");
		left.addComponent(bottomLeftCorner);
	}

	private void initContactAddRemoveButtons() {
		// New item button
		bottomLeftCorner.addComponent(new Button("+", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Object id = contactList.addItem();
				contactList.setValue(id);
			}
		}));

		// Remove item button
		contactRemovalButton = new Button("-", new Button.ClickListener() {
			@Override
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
			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				Object id = contactList.getValue();
				contactEditor.setItemDataSource(id == null ? null : contactList.getItem(id));
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
				@Override
				public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
					// the GAEContainer does not use the vaadin container interface for filtering
					addressBookData.removeFilters(pn);// removeContainerFilters(pn);
					if (sf.toString().length() > 0 && !pn.equals(sf.toString())) {
						addressBookData.addFilter(pn, FilterOperator.EQUAL, sf.toString()); // addContainerFilter(pn,
																							// sf.toString(), true,
																							// false);
					}
					getMainWindow().showNotification("" + addressBookData.size() + " matches found");

				}
			});
		}
	}
}
