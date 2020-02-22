class ObservationAdapter:
  def __init__(self, obs_text):
    self.m_update(obs_text)
    self.m_save_old()
  
  def update(self,obs_text):
    self.m_save_old()
    self.m_update(obs_text)
    
  def m_update(self,obs_text):
    obs = json.loads(obs_text)
    self.ga=GridAccessor(-1,-1,-1,1,1,1)
    self.x=obs[u'XPos']
    self.y=obs[u'YPos']
    self.z=obs[u'ZPos']
    self.yaw=obs[u'Yaw']
    self.pitch=obs[u'Pitch']
    self.grid= obs.get(u'nbr3x3', 0)
    self.east=self.ga.get_cell(self.grid,2,1,1)
    self.west=self.ga.get_cell(self.grid,0,1,1)
    self.north=self.ga.get_cell(self.grid,1,1,0)
    self.south=self.ga.get_cell(self.grid,1,1,2)
  
  def m_save_old(self):
    self.xo = self.x
    self.yo = self.y
    self.zo = self.z
    self.pitcho = self.pitch
    self.yawo = self.yaw