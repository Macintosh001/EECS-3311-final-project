## Bug Report 

### Problems with interface layout
Explanation: 

In "Manage What's in Stock" window, Two buttons were overlapping

Solution:
 
With the help of MigLayout Manager, we repostionted the buttons

Here is the link to the bug in our Issues section: 

 [Problems Wtith Interface Layout](https://github.com/Macintosh001/EECS-3311-final-project/issues/87)
 
--- 
 
### User interface fractional digit display problem
Explanation:

For the price table, too many decimal places was shown

Solution:

The TableEntryGenerator was changed to truncate the floating point number

Here is the link to the bug in our Issues section:

[User Interface Fractional Digit Display Problem](https://github.com/Macintosh001/EECS-3311-final-project/issues/86)

---

### Customer use coupon problem 

Explanation:

After the user has used a coupon once, the last discount code will be used for future purchases
automatically. No new coupons can be used.

solution:

Inside the clearShoppingCart() function, a code to reset the coupon was added.

Here is the link to the bug in our Issues section:

[Customer Use Coupon Problem](https://github.com/Macintosh001/EECS-3311-final-project/issues/85)

---

### There is no hint of an upper limit when setting Quantity
Explanation:

When the user enters a large ints for Quantity, an error code "Quantity must be a whole number" is
generated which is wrong.

Solution: 

In our QuantityValidator Class, the change was made to give the user the proper error massage

[There Is No Hint of an Upper Limit When Setting Quantity](https://github.com/Macintosh001/EECS-3311-final-project/issues/84)

---

## Price setting problem
Explanation:

There is a problem with the inconsisten display when the user enters a large number

Solution:

The max Price was set to $100'000 to fix the issue

[Price Setting Problem](https://github.com/Macintosh001/EECS-3311-final-project/issues/83)


 
 
 
 
