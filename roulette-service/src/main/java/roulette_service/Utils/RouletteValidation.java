package roulette_service.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RouletteValidation {

    private RouletteValidation(){
        return;
    }

    public static boolean validNums(int[] nums, int min, int maxInclusive){
        for (int num : nums){
            if (num > maxInclusive || num < min) return false;
        }
        return true;
    }

    public static boolean hasDuplicates(int[] nums){
        if (nums.length < 2) return false;
        
        ArrayList<Integer> numsList = new ArrayList<>();
        
        for (int num: nums){
            numsList.add(num);
        }

        for (int num: numsList){
            if (Collections.frequency(numsList, num) > 1){
                return true;
            }
        }
        return false;
    }

    public static boolean hasIncrementOne(int[] nums){
        Arrays.sort(nums);
        Set<Integer> diff = new HashSet<Integer>();
        Set<Integer> setOne = new HashSet<>();
        setOne.add(1);
    
        for (int i = 0; i < nums.length - 1; i++){
            diff.add(nums[i+1] - nums[i]); 
        }
        return diff.equals(setOne);
    }

    public static int smallestNumber(int[] nums){
        int finalNumber = nums[0];
        for (int num : nums){
            if (num < finalNumber) finalNumber = num;
        }
        return finalNumber;
    }
}
