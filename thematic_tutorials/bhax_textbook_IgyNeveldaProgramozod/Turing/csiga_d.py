from __future__ import print_function
# ------------------------------------------------------------------------------------------------
# Copyright (c) 2016 Microsoft Corporation
# 
# Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
# associated documentation files (the "Software"), to deal in the Software without restriction,
# including without limitation the rights to use, copy, modify, merge, publish, distribute,
# sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included in all copies or
# substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
# NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
# NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
# DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
# ------------------------------------------------------------------------------------------------

# Tutorial sample #2: Run simple mission using raw XML

# Added modifications by Norbert BĂĄtfai (nb4tf4i) batfai.norbert@inf.unideb.hu, mine.ly/nb4tf4i.1
# 2018.10.18, https://bhaxor.blog.hu/2018/10/18/malmo_minecraft
# 2020.02.02, NB4tf4i's Red Flowers, http://smartcity.inf.unideb.hu/~norbi/NB4tf4iRedFlowerHell


from builtins import range
import MalmoPython
import os
import sys
import time
import random
import json
import math

if sys.version_info[0] == 2:
    sys.stdout = os.fdopen(sys.stdout.fileno(), 'w', 0)  # flush print output immediately
else:
    import functools
    print = functools.partial(print, flush=True)

# Create default Malmo objects:

agent_host = MalmoPython.AgentHost()
try:
    agent_host.parse( sys.argv )
except RuntimeError as e:
    print('ERROR:',e)
    print(agent_host.getUsage())
    exit(1)
if agent_host.receivedArgument("help"):
    print(agent_host.getUsage())
    exit(0)

# -- set up the mission -- #
missionXML_file='csiga_d.xml'
with open(missionXML_file, 'r') as f:
    print("NB4tf4i's Red Flowers (Red Flower Hell) - DEAC-Hackers Battle Royale Arena\n")
    print("The aim of this first challenge, called nb4tf4i's red flowers, is to collect as many red flowers as possible before the lava flows down the hillside.\n")
    print("Norbert Bátfai, batfai.norbert@inf.unideb.hu, https://arato.inf.unideb.hu/batfai.norbert/\n\n")    
    print("Loading mission from %s" % missionXML_file)
    mission_xml = f.read()
    my_mission = MalmoPython.MissionSpec(mission_xml, True)
    my_mission.drawBlock( 0, 0, 0, "lava")


class Hourglass:
    def __init__(self, charSet):
        self.charSet = charSet
        self.index = 0
    def cursor(self):
        self.index=(self.index+1)%len(self.charSet)
        return self.charSet[self.index]

hg = Hourglass('|/-\|')

class GridAccessor:
  def __init__(self,x_min, y_min, z_min,x_max, y_max, z_max):
    self.x_range = x_max-x_min+1
    self.y_range = y_max-y_min+1
    self.z_range = z_max-z_min+1
  def get_cell( self, grid , x , y, z):
    return grid[x + self.z_range * (z + self.y_range * y)]

    
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
          
class SteveIdlingState:
  def __init__(self, steve):
    self.steve=steve
  
  def update(self):
    if abs(self.steve.wp.x-self.steve.obs.x)<=0.5 and abs(self.steve.wp.z-self.steve.obs.z)<=0.5:
      self.steve.wp.next()
    self.steve.change_state(SteveMoveToPointState(steve,self.steve.wp.x,self.steve.wp.z,self))

class SteveMoveToPointState:
  def __init__(self, steve, x,z,next_state):
    self.steve=steve
    self.waiting=False
    self.x=x
    self.z=z
    self.i=0
    self.next_state=next_state
    
  def update(self):
    x_changed = (abs(self.steve.obs.xo-self.steve.obs.x)>.5)
    z_changed = (abs(self.steve.obs.zo-self.steve.obs.z)>.5)
    if self.waiting == True:
      if x_changed or z_changed :
        self.waiting = False
        self.i = 0
      else:
        return
    if self.waiting == False:
      dx = self.x-self.steve.obs.x
      dz = self.z-self.steve.obs.z
      targetblock = ""
      dir=""
      if dx>0.5:
        dir="east"
        targetblock = self.steve.obs.east
      elif dx<-0.5:
        dir="west"
        targetblock = self.steve.obs.west
      elif dz>0.5:
        dir="south"
        targetblock = self.steve.obs.south
      elif dz<-0.5:
        dir="north"
        targetblock = self.steve.obs.north
      else :
        self.steve.change_state(self.next_state)
        return
      action =""
      if targetblock != "air":
        action = "jump"
      else:
        action = "move"
      cmd = action+dir+" 1"      
      self.steve.agent_host.sendCommand( cmd )
      self.waiting = True
    
class Steve:
  def __init__(self, agent_host):
      self.agent_host = agent_host
      self.nof_red_flower = 0
      self.state=SteveIdlingState(self)
  
  def change_state(self,state):
    self.state=state
  
  def run(self):
    world_state = self.agent_host.getWorldState()
    while world_state.is_mission_running and not (len(world_state.observations)>0 and not world_state.observations[-1].text=="{}"):
      time.sleep(0.1)
      world_state = self.agent_host.getWorldState()
    self.obs = ObservationAdapter(world_state.observations[-1].text)
    self.wp = WaypointIter(self.obs.x,self.obs.z,self.obs.yaw,3)
    while world_state.is_mission_running:
      time.sleep(0.1)
      if len(world_state.observations)>0:
        self.obs.update(world_state.observations[-1].text)
        
        self.state.update()
      world_state = self.agent_host.getWorldState()
  
num_repeats = 1
for ii in range(num_repeats):

    my_mission_record = MalmoPython.MissionRecordSpec()

    # Attempt to start a mission:
    max_retries = 6
    for retry in range(max_retries):
        try:
            agent_host.startMission( my_mission, my_mission_record )
            break
        except RuntimeError as e:
            if retry == max_retries - 1:
                print("Error starting mission:", e)
                exit(1)
            else:
                print("Attempting to start the mission:")
                time.sleep(2)

    # Loop until mission starts:
    print("   Waiting for the mission to start ")
    world_state = agent_host.getWorldState()

    while not world_state.has_mission_begun:
        print("\r"+hg.cursor(), end="")
        time.sleep(0.15)
        world_state = agent_host.getWorldState()
        for error in world_state.errors:
            print("Error:",error.text)

    print("NB4tf4i Red Flower Hell running\n")
    steve = Steve(agent_host)
    steve.run()
    print("Number of flowers: "+ str(steve.nof_red_flower))

print("Mission ended")
# Mission has ended.