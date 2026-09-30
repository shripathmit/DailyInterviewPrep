import java.util.ArrayList;
import java.util.List;

public class CheckMaxSubArray {
    public static void main(String[] args) {
        int[] inputA = {-2, 1, -3, 4, -1};

        // Track the maximums
        int maxSoFar = inputA[0];
        int currentMax = inputA[0];

        // Track indices for the best subarray
        int start = 0;
        int end = 0;
        int tempStart = 0;

        for (int i = 1; i < inputA.length; i++) {
            // Decide whether to add the current element to the existing subarray
            // or start a new subarray from the current element
            if (inputA[i] > currentMax + inputA[i]) {
                currentMax = inputA[i];
                tempStart = i; // Potential start of a new max subarray
            } else {
                currentMax = currentMax + inputA[i];
            }

            // Update the global maximum and final boundaries
            if (currentMax > maxSoFar) {
                maxSoFar = currentMax;
                start = tempStart;
                end = i;
            }
        }

        // Build the result list from the best boundaries found
        List<Integer> output = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            output.add(inputA[i]);
        }

        System.out.println("Maximum Subarray: " + output);
        System.out.println("Maximum Sum: " + maxSoFar);
    }
}
