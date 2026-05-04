
package in.gadgethub.dao;

import in.gadgethub.pojo.CartPojo;
import java.util.List;

public interface CartDao {

    String addProduct(CartPojo cart);

    String updateProductInCart(CartPojo cart);

    List<CartPojo> getAllCartItems(String userId);

    int getCartItemCount(String userId, String itemId);

    String removeProductFromCart(String userId, String prodId);

    boolean removeAProduct(String userId, String prodId);
}
