package roulette_service.Handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.View.IRouletteGameView;
import roulette_service.View.RouletteGameView;

public class RouletteHandler implements IRouletteHandler {

    @Override
    public Optional<IRouletteGameView> createGame(IRouletteGameStartRequest rouletteGameStartRequest) {
        Random random = new Random();
        String betType = rouletteGameStartRequest.getBetType();
        int[] nums = rouletteGameStartRequest.getBet();
        float amount = rouletteGameStartRequest.getAmount();
        int result = random.nextInt(37);
        boolean isWin = false;
        float payout = 0;
        RouletteGameView rouletteGameView = null;
    
        if (!validNums(nums, 0, 36) || hasDuplicates(nums)) return Optional.empty();

        switch (betType) {
            
            case "single":

                if(nums.length != 1) return Optional.empty();
                
                isWin = (result == nums[0]);
                payout = isWin? amount * 35 : - amount;
                
                break;

            case "split":
                int smallerNum = Math.min(nums[0], nums[1]);
                int biggerNum = Math.max(nums[0], nums[1]);
                int diffNums = Math.abs(nums[0] - nums[1]);

                if ((nums.length != 2) ||
                    ((smallerNum % 3 == 0 && !(biggerNum % 3 == 0)) && smallerNum != 0) ||
                    ((diffNums != 1 && diffNums != 3) && smallerNum != 0) ||
                    (smallerNum == 0 && diffNums > 3)){
                    return Optional.empty();
                }
                isWin = (result == nums[0]) || (result == nums[1]);
                payout = isWin? amount * 17 : - amount;
                break;

            case "corner":
                if(nums.length != 1 ||
                    smallestNumber(nums) % 3 == 0 ||
                    !validNums(nums, 0, 33))
                    return Optional.empty();
                
                int[] bet = {nums[0], nums[0] + 1, nums[0] + 3, nums[0] + 4 };
                Set<Integer> betSet = new HashSet<Integer>();

                for (int i = 0; i < 4; i++){
                    betSet.add(bet[i]);
                }
                nums = bet;
                isWin = betSet.contains(result);
                payout = isWin? amount * 8 : - amount;
                break;

            case "sixLine":
                if(nums.length != 1 ||
                    smallestNumber(nums) % 3 != 1 ||
                    !validNums(nums, 0, 33))
                    return Optional.empty();
                
                int[] betSix = new int[6];
                Set<Integer> betSetSix = new HashSet<Integer>();

                for (int i = 0; i < 6; i++){
                    betSix[i] = (nums[0] + i);
                    betSetSix.add(nums[0] + i);
                }
                nums = betSix;
                isWin = betSetSix.contains(result);
                payout = isWin? amount * 5 : - amount;
                break;

            case "street":
                int smallestNumber = smallestNumber(nums);
                int numSum = 0;
                for (int num : nums){
                    numSum += num;
                }

                boolean invalidZero = (smallestNumber == 0) && 
                                      (!validNums(nums, 0, 3) || (numSum != 3 && numSum != 5));
                boolean invalidNonZero = (smallestNumber != 0) &&
                                         (!validNums(nums, smallestNumber, smallestNumber + 2) ||
                                          !(hasIncrementOne(nums)) || smallestNumber % 3 != 1 );

                if (invalidZero || invalidNonZero) return Optional.empty();

                Set<Integer> betSetStreet = new HashSet<Integer>();

                for (int i = 0; i < 3; i++){
                    betSetStreet.add(nums[i]);
                }
                isWin = betSetStreet.contains(result);
                payout = isWin? amount * 11 : - amount;
                break;

            
            case "red", "black":
                Set<Integer> redNums = Set.of(1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36);
            
                isWin = (result !=0 && betType.equals("red"))? 
                        redNums.contains(result) : !redNums.contains(result);
                
                payout = isWin? amount : - amount;
                break;
            
            case "even", "odd":
                isWin = (result!= 0 && betType.equals("even"))?
                result % 2 == 0 : result % 2 == 1;

                payout = isWin? amount : - amount;
                break;

            case "low", "high":
                isWin = (result!= 0 && betType.equals("low"))?
                result < 19 : result >= 19;

                payout = isWin? amount : - amount;
                break;

            case "firstDozen", "secondDozen", "thirdDozen":
                if(result > 24 ){
                    isWin = betType.equals("thirdDozen");
                } else if (result > 12){
                    isWin = betType.equals("secondDozen");
                } else if (result > 0){
                    isWin = betType.equals("firstDozen");
                }
                
                payout = isWin? amount * 2 : - amount;
                break;

            case "firstColumn", "secondColumn", "thirdColumn":
                if (betType.equals("firstColumn")){
                    isWin = result % 3 == 1;
                } else if (betType.equals("secondColumn")){
                    isWin = result % 3 == 2;
                } else{
                    isWin = result % 3 == 0;
                } 

                if (result == 0){
                    isWin = false;
                }

                payout = isWin? amount * 2 : - amount;
                break;

            default:
                return Optional.empty();
            
        }
        rouletteGameView = new RouletteGameView(
                    betType,
                    nums,
                    amount,
                    result,
                    isWin,
                    payout
        );
        return Optional.of(rouletteGameView);
    }
    
    private boolean validNums(int[] nums, int min, int maxInclusive){
        for (int num : nums){
            if (num > maxInclusive || num < min) return false;
        }
        return true;
    }

    private boolean hasDuplicates(int[] nums){
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

    private boolean hasIncrementOne(int[] nums){
        Arrays.sort(nums);
        Set<Integer> diff = new HashSet<Integer>();
        Set<Integer> setOne = new HashSet<>();
        setOne.add(1);
    
        for (int i = 0; i < nums.length - 1; i++){
            diff.add(nums[i+1] - nums[i]); 
        }
        return diff.equals(setOne);
    }

    private int smallestNumber(int[] nums){
        int finalNumber = nums[0];
        for (int num : nums){
            if (num < finalNumber) finalNumber = num;
        }
        return finalNumber;
    }
}
