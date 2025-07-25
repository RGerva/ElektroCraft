/**
 * Interface: IEnergyPacketUpdate Defines the contract for implementations of this type.
 *
 * <p>Created by: D56V1OK On: 2025/jul.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.network.interfaces;

public interface IEnergyPacketUpdate {
  void setEnergy(int energy);

  void setCapacity(int capacity);
}
