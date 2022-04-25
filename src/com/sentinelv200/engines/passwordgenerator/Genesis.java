package com.sentinelv200.engines.passwordgenerator;

import com.sentinelv200.Utility.ToolBox;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * @author Shalin Kulawardane
 */
public class Genesis implements PasswordGenerator{
    private Set<Character> serviceName;
    private final int PASSWORD_LENGTH;
    private final int[] SEGMENT_LENGTHS;
    
    public Genesis(Set<Character> serviceName, int length) {
        this.serviceName = serviceName;
        this.PASSWORD_LENGTH = length;
        this.SEGMENT_LENGTHS = computeSegmentLengths();
    }

    public Genesis(String serviceName, int length) {
        this.serviceName = ToolBox.convertToHashSet(serviceName.toLowerCase().toCharArray());
        this.PASSWORD_LENGTH = length;
        this.SEGMENT_LENGTHS = computeSegmentLengths();
    }

    //---  Start of setters and getters ---

    public void setServiceName(Set<Character> fullServiceName) {
        this.serviceName = fullServiceName;
    }

    public int[] getSegmentLengths() {
        return this.SEGMENT_LENGTHS;
    }
 
    // --- End of setters and getters ---
    
    @Override
    public boolean runDiagnosis() {
        return false;
    }
    
    // TODO: Fix the run method
    @Override
    public void run() { 
        generatePassword();
    }

    /** 
     * Responsible for obtaining the segments of the password, merging them
     * and returning the final password.
     *  
     * @return A byte[] of the password. It returns a byte[] array, so that it can be eligible for encryption.
    */
    @Override
    public byte[] generatePassword() {
        byte[] segmentOne = constructSegmentOne();
        byte[] segmentTwo = constructSegmentTwo();
        byte[] result2 = new byte[segmentOne.length + segmentTwo.length];
        System.arraycopy(segmentOne, 0, result2, 0, segmentOne.length);
        System.arraycopy(segmentTwo, 0, result2, segmentOne.length, segmentTwo.length);        
        return result2;		
    }

    /**
     * Responsible for constructing the first segment of the password.
     * This segment is dependent on the service name from the user.
     * 
     * @return The first segment, in the form of a byte[]
    */
    private byte[] constructSegmentOne() {
        final Character[] NEW_SERVICE_NAME = shuffle(trimServiceName(this.serviceName));        
        final int LENGTH = NEW_SERVICE_NAME.length;
        final char[] USED_ELEMENTS = new char[LENGTH];
        final StringBuilder STRING_BUILDER = new StringBuilder(LENGTH);                
        int i = 0;

        for(Character element : NEW_SERVICE_NAME) {
            if(!elementUsed(Character.toLowerCase(element), USED_ELEMENTS)) {
                STRING_BUILDER.append((i % 2 == 0) ? Character.toUpperCase(element) : Character.toLowerCase(element));
                USED_ELEMENTS[i] = Character.toLowerCase(element); }
            i++;       
        }               
        return new String(STRING_BUILDER).getBytes();		
    }

    /**
     * Responsible for constructing the second segment of the password.
     * This segment utilizes special characters and numbers.
     * It also tries to ensure randomness in the order of the elements.
     * 
     * @return The second segment, in the form of a byte[]
    */
    private byte[] constructSegmentTwo() {
        final char[] SPECIAL_CHARS = {'!', '@', '#', '$', '%', '^', '&', '*', '+', '/', ' '};
        final char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        final int LENGTH = this.SEGMENT_LENGTHS[1];
        final byte[] RESULT = new byte[LENGTH];
        int charIndex = 0;
        int intIndex = 0;
        
        shuffle(SPECIAL_CHARS);
        shuffle(NUMBERS);

        for(int i = 0; i < LENGTH; i++) {
            if(i % 2 == 0) {
                RESULT[i] = (byte)SPECIAL_CHARS[charIndex];
                charIndex ++;
            } else {
                RESULT[i] = (byte)NUMBERS[intIndex];
                intIndex ++;            
            }

            // Reset charIndex to start using special characters from the begining:
            if(charIndex == (SPECIAL_CHARS.length - 1)) {
                shuffle(SPECIAL_CHARS);
                charIndex = 0;
            }

            // Reset intIndex to start using numbers from the begining:
            if(intIndex == (NUMBERS.length - 1)) {
                shuffle(NUMBERS);
                intIndex = 0;            
            }
        }
        return RESULT;
    }

    /**
     * Responsible for identifying the length of each segment.
     * @return A lengths for both segments in an int[]. Index 0 is the segment 1 length and Index 1 is the segment 2 length
    */
    private int[] computeSegmentLengths() {        
        final int LENGTH_PER_SEGMENT = this.PASSWORD_LENGTH / 2;
        final int SEGMENT_ONE_LENGTH = LENGTH_PER_SEGMENT;        
        final int difference = SEGMENT_ONE_LENGTH - this.serviceName.size();
        int segmentTwoLength = (this.PASSWORD_LENGTH % 2 == 0) ? LENGTH_PER_SEGMENT : LENGTH_PER_SEGMENT + 1;
        segmentTwoLength = (difference > 0) ? segmentTwoLength + difference : segmentTwoLength;        
        return new int[]{SEGMENT_ONE_LENGTH, segmentTwoLength};
    }

    private static boolean elementUsed(char element, char[] array) {
        for(char character : array) { 
            if(element == character) { 
                return true; 
            } 
        }
        return false;    
    }
   
    /** 
    * Responsible for trimming the service name to abide by the segment length constraint.
    * @param source The full service name
    * @return A Set<Characters> of the remaining (non-trimmed) characters.
    */
    public Set<Character> trimServiceName(Set<Character> source) {        
        final Set<Character> DESTINATION = new HashSet<>();        
        final Iterator<Character> ITERATOR = source.iterator();
        final int LENGTH = this.SEGMENT_LENGTHS[0];
        
        for(int i = 0; (i < LENGTH && i < source.size()); i++) { 
            DESTINATION.add(ITERATOR.next()); 
        }
        return DESTINATION;        
    } 

    private static void shuffle(char[] array) {
        final Random RANDOM = new Random();
        final int LENGTH = array.length;
        
        for(int i = 0; i < LENGTH; i++) {
            int randomIndex1 = RANDOM.nextInt(LENGTH);
            int randomIndex2 = RANDOM.nextInt(LENGTH);
            char temp = array[randomIndex1];
            array[randomIndex1] = array[randomIndex2];
            array[randomIndex2] = temp;
        }
    }

    private static Character[] shuffle(Set<Character> set) {
        final Character[] ARRAY =  new Character[set.size()];
        set.toArray(ARRAY);
        final Random RANDOM = new Random();
        final int LENGTH = ARRAY.length;

        for(int i = 0; i < LENGTH; i++) {
            int randomIndex1 = RANDOM.nextInt(LENGTH);
            int randomIndex2 = RANDOM.nextInt(LENGTH);
            char temp = ARRAY[randomIndex1];
            ARRAY[randomIndex1] = ARRAY[randomIndex2];
            ARRAY[randomIndex2] = temp;            
        }
        return ARRAY;
    }
}