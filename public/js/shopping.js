"use strict";

class SaleItem {

    constructor(product, quantity) {
        // only set the fields if we have a valid product
        if (product) {
            this.product = product;
            this.quantityPurchased = quantity;
            this.salePrice = product.listPrice;
        }
    }

    getItemTotal() {
        return this.salePrice * this.quantityPurchased;
    }

}

class ShoppingCart {

    constructor() {
        this.saleItemList = new Array();
    }

    reconstruct(sessionData) {
        for (let item of sessionData.saleItemList) {
            this.addItem(Object.assign(new SaleItem(), item));
        }
    }

    getItems() {
        return this.saleItemList;
    }

    addItem(item) {
        this.saleItemList.push(item);
    }

    setCustomer(customer) {
        this.customer = customer;
    }

    getTotal() {
        let total = 0;
        for (let item of this.saleItemList) {
            total += item.getItemTotal();
        }
        return total;
    }

}

// create a new module, and load the other pluggable modules
var module = angular.module('ShoppingApp', ['ngResource', 'ngStorage']);

module.factory('productDAO', function ($resource) {
    return $resource('/api/products/:id');
});

module.factory('categoryDAO', function ($resource) {
    return $resource('/api/categories/:cat');
});

module.factory('registerDAO', function ($resource) {
    return $resource('/api/register');
});

module.factory('signInDAO', function ($resource) {
    return $resource('/api/customer/:username');
});

module.factory('validateDAO', function ($resource) {
    return $resource('/api/validate/:values');
});

module.factory('saleDAO', function ($resource) {
    return $resource('/api/sales');
});

module.factory('cart', function ($sessionStorage) {
    let cart = new ShoppingCart();

    // is the cart in the session storage?
    if ($sessionStorage.cart) {

        // reconstruct the cart from the session data
        cart.reconstruct($sessionStorage.cart);
    }

    return cart;
});

module.controller('ProductController', function (productDAO, categoryDAO) {
    // load the products
    this.products = productDAO.query();
    // load the categories
    this.categories = categoryDAO.query();
    
    // click handler for the category filter buttons
    this.selectCategory = function (selectedCat) {
        this.products = categoryDAO.query({"cat": selectedCat});
    };
    
    // click handler for the category filter buttons
    this.getAll = function () {
        this.products = productDAO.query();
    };
    
});

module.controller('ShoppingController', function (cart, $sessionStorage, $window, saleDAO) {
    // load the items and total
    this.items = cart.getItems();
    this.total = cart.getTotal();
    
    //load selected product into var for buy page
    this.selectedProduct = $sessionStorage.selectedProduct;
    
    this.saveProduct = function(product){
        $sessionStorage.selectedProduct = product;
        $window.location.href = 'buy.html';
        
    };
    
    this.addToCart = function(quantity){
        var product = $sessionStorage.selectedProduct;
        //initialize new SaleItem
        let item = new SaleItem(product, quantity);
        //add item to cart
        cart.addItem(item);
        
        $sessionStorage.cart = cart;
        $window.location.href = 'viewProducts.html';
    };
    
    this.checkOut = function(){
        var customer = $sessionStorage.customer;
        
        cart.setCustomer(customer);
        saleDAO.save(cart);
        
        delete $sessionStorage.cart;
        
        $window.location.href = 'orderComplete.html';
    };
    
});

module.controller('CustomerController', function (registerDAO, $sessionStorage, $window, validateDAO) {
    this.signedIn = false;
    
    this.registerCustomer = function (customer) {
        if(/\s/.test(customer.username) || /\s/.test(customer.password)){
            alert("Username and Password cannot contain any whitespace");
        }else{
        registerDAO.save(null, customer);
        console.log(customer);
        // redirect to signin
        $window.location.href = 'signin.html';
    }
    };
    
    this.signInMessage = "Please sign in to continue.";
    
    // alias 'this' so that we can access it inside callback functions
    let ctrl = this;
    
    this.signIn = function (username, password) {
       
                // get customer from web service
           validateDAO.get({'values': username + " " + password},
               // success
               function (customer) {
                   // also store the retrieved customer
                   $sessionStorage.customer = customer;
                   // redirect to home
                   $window.location.href = '.';
               },
               // fail
               function () {
                   ctrl.signInMessage = 'Sign in failed. Please try again.';
               }
           );
        
       
    };
    
    this.checkSignIn = function() {
        if($sessionStorage.customer)  {
            this.signedIn = true;
            this.welcome = "Welcome\n" + $sessionStorage.customer.firstname + " " + $sessionStorage.customer.surname;
        }
    };
    
    this.signOut = function() {
        delete $sessionStorage.customer;
        this.signedIn = false;
        this.welcome = "";
    };
});