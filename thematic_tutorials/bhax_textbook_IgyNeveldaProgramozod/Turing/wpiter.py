class WaypointIter:
    def __init__(self,init_x,init_z,init_yaw,incr):
      self.x=init_x
      self.z=init_z
      yawrem = init_yaw % 90
      self.yaws = [0+yawrem,90+yawrem,180+yawrem,270+yawrem]
      self.idx = self.yaws.index(init_yaw)
      self.yaw=init_yaw
      self.incr=incr
      self.dist_mult = 1
      self.m_calc()
      
    def next(self):
        self.dist_mult += 0.5
        self.idx=(self.idx+1)%4
        self.m_calc()
          
    def m_calc(self):
        dist = math.floor(self.dist_mult)*self.incr
        self.yaw = self.yaws[self.idx]
        if self.yaw==0:
          self.z=self.z+dist
        elif self.yaw==90:
          self.x=self.x-dist
        elif self.yaw==180:
          self.z=self.z-dist
        else:
          self.x=self.x+dist