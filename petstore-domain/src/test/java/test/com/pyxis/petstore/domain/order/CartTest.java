package test.com.pyxis.petstore.domain.order;

import com.pyxis.petstore.domain.order.Cart;
import com.pyxis.petstore.domain.order.CartItem;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static test.support.com.pyxis.petstore.builders.ItemBuilder.anItem;
import static test.support.com.pyxis.petstore.matchers.SerializedForm.serializedForm;

public class CartTest {
    Cart cart = new Cart();

    @Test public void
    isEmptyByDefault() {
        assertTrue("contains item(s)", cart.isEmpty());
        assertThat("grand total", cart.getGrandTotal(), equalTo(BigDecimal.ZERO));
        assertThat("total quantity", cart.getTotalQuantity(), equalTo(0));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    containsCartItemsInBuyingOrder() {
        String[] itemNumbers = { "11111111", "22222222", "33333333" };
        for (String itemNumber : itemNumbers) {
            cart.add(anItem().withNumber(itemNumber).build());
        }
        assertTrue("empty cart", !cart.isEmpty());
        assertThat("items", cart.getItems(), containsItems(
                number("11111111"),
                number("22222222"),
                number("33333333")));
        assertThat("total quantity", cart.getTotalQuantity(), equalTo(3));
    }

    @Test(expected = UnsupportedOperationException.class) public void
    listOfItemsCannotBeModified() {
        cart.getItems().clear();
    }

    @Test public void
    calculatesGrandTotal() {
        String[] prices = { "50", "75.50", "12.75" };
        BigDecimal expectedTotal = new BigDecimal("138.25");

        for (String price : prices) {
            cart.add(anItem().priced(price).build());
        }
        assertThat("grand total", cart.getGrandTotal(), equalTo(expectedTotal));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    groupsItemsByNumber() {
        String[] itemNumbers = { "11111111", "11111111", "22222222" };

        for (String number : itemNumbers) {
            cart.add(anItem().withNumber(number).build());
        }
        assertThat("cart", cart, aCartContaining(
                itemWith(number("11111111"), quantity(2)),
                itemWith(number("22222222"), quantity(1))));
        assertThat("total quantity", cart.getTotalQuantity(), equalTo(3));
    }
    
    @Test public void
    canBeCleared() {
        havingAddedItemsToCart();

        cart.clear();
        assertTrue("contains item(s)", cart.isEmpty());
    }

    @SuppressWarnings("unchecked")
    @Test public void
    isSerializable() {
        cart.add(anItem().withNumber("11111111").build());
        assertThat("cart", cart, serializedForm(aCartContaining(itemWith(number("11111111")))));
    }

    private void havingAddedItemsToCart() {
        cart.add(anItem().build());
        cart.add(anItem().build());
        cart.add(anItem().build());
    }
                                                                            
    private Matcher<Cart> aCartContaining(Matcher<CartItem>... cartItemMatchers) {
        return new FeatureMatcher<Cart, Iterable<CartItem>>(containsItems(cartItemMatchers), "a cart containing", "cart content") {
            @Override protected List<CartItem> featureValueOf(Cart actual) {
                return cart.getItems();
            }
        };
    }

    private Matcher<? super Iterable<CartItem>> containsItems(Matcher<CartItem>... cartItemMatchers) {
        return contains(cartItemMatchers);
    }

    private Matcher<CartItem> itemWith(Matcher<CartItem>... cartItemMatchers) {
        return allOf(cartItemMatchers);
    }

    private Matcher<CartItem> quantity(int count) {
        return new FeatureMatcher<CartItem, Integer>(equalTo(count), "an item with quantity", "item quantity") {
            @Override protected Integer featureValueOf(CartItem actual) {
                return actual.getQuantity();
            }
        };
    }

    private Matcher<CartItem> number(String number) {
        return new FeatureMatcher<CartItem, String>(equalTo(number), "an item with number", "item number") {
            @Override protected String featureValueOf(CartItem actual) {
                return actual.getItemNumber();
            }
        };
    }
}