package next.bankly.co.operations.models;

public enum TypeOperation {
    depot, withdrawingFunds, checkBalance, transfer;

    public static boolean contains(String s)
    {
        for(TypeOperation type:values())
            if (type.name().equals(s))
                return true;
        return false;
    }
    }
