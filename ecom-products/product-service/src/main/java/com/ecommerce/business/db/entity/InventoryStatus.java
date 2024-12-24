package com.ecommerce.business.db.entity;

public enum InventoryStatus {
    INSTOCK,
    LOWSTOCK,
    OUTOFSTOCK;

    public static InventoryStatus getStatus(Integer currentQuantity){
        if (currentQuantity != null) {
            if (currentQuantity > 0 && currentQuantity <= 30) {
                return InventoryStatus.LOWSTOCK;
            } else if ( currentQuantity > 30) {
                return InventoryStatus.INSTOCK;
            }
        }
        return InventoryStatus.OUTOFSTOCK;
    }
}