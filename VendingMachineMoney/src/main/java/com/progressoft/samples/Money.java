package com.progressoft.samples;
import java.util.*;

public class Money {
    private final double amount;
    private Map<Money,Integer> map = new HashMap<>();
    public static final Money Zero = new Money(0.0);
    public static final Money OnePiaster = new Money(0.01);
    public static final Money FivePiasters = new Money(0.05);
    public static final Money TenPiasters = new Money(0.10);
    public static final Money TwentyFivePiasters = new Money(0.25);
    public static final Money FiftyPiasters = new Money(0.50);
    public static final Money OneDinar = new Money(1.0);
    public static final Money FiveDinars = new Money(5.0);
    public static final Money TenDinars = new Money(10.0);
    public static final Money TwentyDinars = new Money(20.0);
    public static final Money FiftyDinars = new Money(50.0);
    public Money(double amount) {
        if(amount<0.0){
            throw new IllegalArgumentException("amount must have amount more than or equal zero");
        }
        this.amount = amount;
    }

    public double amount() {
        return this.amount;
    }
    public Money times(int count) {
        //check if count non-negative
        if(count>=0){
            Save(this,count);
            Money money = new Money(this.amount*count);
            money.map = this.map;
             return money;
        }
        throw new IllegalArgumentException("The count must be non-negative, Please enter a Valid count");
    }
    public static Money sum(Money... items) {
        double total = 0;
        for (Money item : items) {
            total += item.amount;
        }
        return new Money(total);
    }

    public Money plus(Money other) {
        Save(other,1);
        Money money = new Money(this.amount+other.amount());
        money.map = this.map;
        return money;
    }

    public Money minus(Money other) {
        double subtraction = this.amount - other.amount();
        if(other.amount()>this.amount){
            throw new IllegalArgumentException("subtraction between 2 amount can't be negative");
        }
        else if(subtraction>0 && (!ChangeOperation(map,subtraction) && !ChangeOperation(map,other.amount()))){
                throw new IllegalArgumentException("Cent are not enough for the change");
        }
        Money money = new Money(subtraction);
        money.map = this.map;
        return money;
    }

    public void Save(Money money,int count){
        map.put(money,map.getOrDefault(money,0)+count);
    }
    public boolean ChangeOperation(Map<Money, Integer> map, double target){

        List<Money> Bank = new ArrayList<>(map.keySet());
        Collections.sort(Bank, (a, b) -> Double.compare(b.amount(), a.amount()));

        for (Money money : Bank) {
            double moneyValue = money.amount();
            int count = map.getOrDefault(money, 0);
            int need = (int)(target / moneyValue);
            int used = Math.min(need, count);

            target -= used * moneyValue;
            count -= used;

            if (target == 0){
                return true;
            }
        }
        return target == 0;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Money money = (Money) obj;
        return Double.compare(money.amount,amount)==0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(amount);
    }

    @Override
    public String toString() {
        return String.format("%.2f",this.amount());
    }
}

