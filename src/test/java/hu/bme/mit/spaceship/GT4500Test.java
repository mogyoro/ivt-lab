package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimaryTS;
  private TorpedoStore mockSecondaryTS;
  @Before
  public void init(){
	mockPrimaryTS = mock(TorpedoStore.class);
	mockSecondaryTS = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimaryTS, mockSecondaryTS);
  }

  @Test
  public void fireTorpedos_Single_Success(){
    // Arrange
	when(mockPrimaryTS.isEmpty()).thenReturn(false);
	when(mockPrimaryTS.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    verify(mockPrimaryTS, times(1)).isEmpty();
    verify(mockPrimaryTS, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedos_All_Success(){
    // Arrange
	when(mockPrimaryTS.isEmpty()).thenReturn(false);
	when(mockSecondaryTS.isEmpty()).thenReturn(false);
	when(mockPrimaryTS.fire(1)).thenReturn(true);
	when(mockSecondaryTS.fire(1)).thenReturn(true);
	
    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    verify(mockPrimaryTS, times(1)).isEmpty();
    verify(mockPrimaryTS, times(1)).fire(1);
    verify(mockSecondaryTS, times(1)).isEmpty();
    verify(mockSecondaryTS, times(1)).fire(1);
    assertEquals(true, result);
  }
  
  @Test
  public void fireTorpedos_Single_PrimaryFirst_Success() {
	  //Arrange
	  when(mockPrimaryTS.fire(1)).thenReturn(true);
	  when(mockPrimaryTS.isEmpty()).thenReturn(false);
	  
	  //Act
	  boolean result = ship.fireTorpedos(FiringMode.SINGLE);
	  
	  //Assert
	  verify(mockPrimaryTS, times(1)).fire(1);
	  verify(mockSecondaryTS, never()).fire(anyInt());
	  assertEquals(true, result);
  }
  
  @Test
  public void fireTorpedos_Single_TwoTimes_Alternating_Success() {
	  //Arrange
	  when(mockPrimaryTS.isEmpty()).thenReturn(false);
	  when(mockSecondaryTS.isEmpty()).thenReturn(false);
	  when(mockPrimaryTS.fire(1)).thenReturn(true);
	  when(mockSecondaryTS.fire(1)).thenReturn(true);
	  
	  //Act
	  boolean result = ship.fireTorpedos(FiringMode.SINGLE) && ship.fireTorpedos(FiringMode.SINGLE);
	  
	  //Assert
	  verify(mockPrimaryTS, times(1)).fire(1);
	  verify(mockSecondaryTS, times(1)).fire(1);
	  assertEquals(true, result);
  }
  

}
