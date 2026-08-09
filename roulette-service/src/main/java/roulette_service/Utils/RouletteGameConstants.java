package roulette_service.Utils;

import org.apache.tomcat.util.digester.Rules;

public class RouletteGameConstants {

    public static String RULES = 
    
    """
    ROULETTE RULES

    1. The user places a bet.
    2. A ball is spun on top of a wheel with numbers ranging from 0 to 36.
    3. Whatever square the ball lands on is the winning number.

    The number 0 is green. Numbers 1–36 are either red or black.

    Betting Options and Payouts:

    * Single: Bet on a single number (0–36). Payout: 35:1.
    * Split: Bet on two adjacent numbers. Payout: 17:1.
    * Street: Bet on three numbers in the same row. Payout: 11:1.
    * Corner: Bet on four numbers forming a square. Payout: 8:1.
    * Six Line: Bet on six numbers in two adjacent rows. Payout: 5:1.
    * Red/Black: Bet on red or black. Payout: 1:1.
    * Odd/Even: Bet on an odd or even number. Payout: 1:1.
    * Low/High: Bet on 1–18 or 19–36. Payout: 1:1.
    * Dozens: Bet on 1–12, 13–24, or 25–36. Payout: 2:1.
    * Columns: Bet on one of the three columns of numbers. Payout: 2:1.

    The number 0 does not count as red, black, odd, even, low, high, any dozen or any column.

    A winning bet returns the original wager plus the corresponding profit.
    A losing bet forfeits the wager.
    """;
            
    
    public static String CHANCES = 
    """
    BETTYPE     WINNING_OUTCOMES    CHANCE      PAYOUT
    SINGLE              1            2.70%       35:1
    SPLIT               2            5.41%       17:1
    STREET              3            8.11%       11:1
    CORNER              4           10.81%       8:1
    SIX LINE            6           16.22%       5:1
    RED / BLACK        18           48.65%       1:1
    ODD / EVEN         18           48.65%       1:1
    LOW / HIGH         18           48.65%       1:1
    DOZEN              12           32.43%       2:1
    COLUMN             12           32.43%       2:1

    Expected Value:

    For a standard European roulette bet, the house edge is approximately 2.70%.

    Expected profit can be calculated as:

    Expected Profit = (Win Chance × Profit) - (Loss Chance × Bet Amount)

    For a straight-up bet of €10:

    Expected Profit = (1/37 × €350) - (36/37 × €10)
    Expected Profit ≈ -€0.27

    --> over a large number of bets, the casino has an expected profit of ≈2.70% of the total amount wagered.

    """;
    
    private RouletteGameConstants(){
            return;
        } 
}
