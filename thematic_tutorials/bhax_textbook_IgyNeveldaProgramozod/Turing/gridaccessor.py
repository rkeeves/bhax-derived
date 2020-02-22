class GridAccessor:
  def __init__(self,x_min, y_min, z_min,x_max, y_max, z_max):
    self.x_range = x_max-x_min+1
    self.y_range = y_max-y_min+1
    self.z_range = z_max-z_min+1
  def get_cell( self, grid , x , y, z):
    return grid[x + self.z_range * (z + self.y_range * y)]