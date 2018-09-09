/**
 * 
 */
package com.crossover.techtrial.service;

import com.crossover.techtrial.dto.RideDTO;
import com.crossover.techtrial.dto.TopDriverDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * RideService for rides.
 * @author crossover
 *
 */
public interface RideService {
  
  public RideDTO save(RideDTO ride);
  
  public RideDTO findById(Long rideId);

  public List<TopDriverDTO> getTopDrivers(Long max, LocalDateTime startDate, LocalDateTime endDate);

}
