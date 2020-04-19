// [COMPILE]
// -Wl,-subsystem,windows -lmingw32 -lSDL2main -lSDL2
#include <iostream>
#include <stdexcept>
#include <string>
#include <sstream>
#define SDL_MAIN_HANDLED 
#include <sdl2/SDL.h>
#include <functional>
#include <memory>
#include <cassert>
#include <vector>
#include <map>
#include "SimpleSignal.h"

inline void throw_sdl(const std::string& msg){
  std::stringstream ss;
  ss << msg << " failed SDL_Error: " << SDL_GetError();
  throw std::runtime_error(ss.str());
}

class SDL_Guard
{
//[LIFECYCLE]
public:
    SDL_Guard(Uint32 flags) {if(SDL_Init(flags) != 0)throw_sdl("SDL_Init"); }
    
    ~SDL_Guard(){ SDL_Quit();}
//[API]
//[INTERNAL]
};

class Clock
{
//[LIFECYCLE]
public:
  Clock() : last_ticks(now()) {}
  
  virtual ~Clock() {};
//[API]
public:
  inline Uint32 last() const { return last_ticks;}
  
  inline Uint32 now() const{ return SDL_GetTicks();}
  
  int elapsed() const{ return now() - last_ticks;}
  
  int restart()
  {
    Uint32 cur = SDL_GetTicks();
    Uint32 elapsed = cur - last_ticks;
    last_ticks = cur;
    return elapsed;
  }
//[INTERNAL]
private:
  Uint32 last_ticks;
};

class FPSClock : public Clock
{
//[LIFECYCLE]
public:
  FPSClock(const Uint32 fps_goal) : Clock(),goal_frame_tickcount(1000/fps_goal) {}
  
  virtual ~FPSClock() {};
//[API]
public:
  
  inline int remaining() const{ return goal_frame_tickcount - elapsed();}

//[INTERNAL]
private:
  
  Uint32 goal_frame_tickcount;
};

class Window
{
//[LIFECYCLE]
public: 

  Window(const std::string& title, int width, int height);

  virtual ~Window();
  
  Window(const Window&) = delete;
  
  Window& operator=(const Window&) = delete;
  
  Window(Window&& o) noexcept;
  
  Window& operator=(Window&& o) noexcept;
//[API]
public:
  bool is_shown() const;
  
  Window& show();

  Window& hide();

  SDL_Window* ptr() const;
//[INTERNAL]
private:
  SDL_Window* window;
};

Window::Window(const std::string& title, int width, int height)
{
  window = SDL_CreateWindow( title.c_str(), SDL_WINDOWPOS_CENTERED,  SDL_WINDOWPOS_CENTERED,  width,  height, SDL_WINDOW_SHOWN );
  if( window == nullptr )throw_sdl("CreateWindow");
}

Window::~Window(){if (window != nullptr)SDL_DestroyWindow(window);}

Window::Window(Window&& o) noexcept : window(o.window){o.window=nullptr;};

Window& Window::operator=(Window&& o) noexcept
{
  if (&o == this)return *this;
  if (window != nullptr)SDL_DestroyWindow(window);
  window = o.window;
  o.window = nullptr;
  return *this;
}

bool Window::is_shown() const
{
  int flag = SDL_GetWindowFlags(window);
  return (flag & SDL_WINDOW_SHOWN) ? true : false;
}

Window& Window::show(){SDL_ShowWindow(window);return *this;}

Window& Window::hide(){SDL_HideWindow(window);return *this;}

SDL_Window* Window::ptr() const{ return window;};

class Color : public SDL_Color {
//[LIFECYCLE]
public:

	constexpr Color() : SDL_Color{0, 0, 0, 0} {}

	constexpr Color(const SDL_Color& color) : SDL_Color{color.r, color.g, color.b, color.a} {}

	constexpr Color(Uint8 r, Uint8 g, Uint8 b) : SDL_Color{r, g, b, SDL_ALPHA_OPAQUE} {}
  
	constexpr Color(Uint8 r, Uint8 g, Uint8 b, Uint8 a) : SDL_Color{r, g, b, a} {}

};

class Vec2D : public SDL_Point
{
//[LIFECYCLE]
public:
  
  constexpr Vec2D() : SDL_Point{0,0}{}
  
  constexpr Vec2D(int x, int y) : SDL_Point{x,y}{}
//[API]
public:
  constexpr Vec2D operator+(const Vec2D& o) const{return Vec2D(x + o.x, y + o.y);}

	constexpr Vec2D operator-(const Vec2D& o) const {return Vec2D(x - o.x, y - o.y);}
  
  Vec2D& operator+=(const Vec2D& other) {
		x += other.x;
		y += other.y;
		return *this;
	}

	Vec2D& operator-=(const Vec2D& other) {
		x -= other.x;
		y -= other.y;
		return *this;
	}
};

class Rect : public SDL_Rect
{
//[LIFECYCLE]
public:
  
  constexpr Rect() : SDL_Rect{0, 0, 0, 0} {}

  constexpr Rect(const SDL_Rect& r) : SDL_Rect{r.x,r.y,r.w,r.h}{}
   
  constexpr Rect(int x, int y, int w, int h) : SDL_Rect{x,y,w,h}{}
  
  constexpr Rect(const Vec2D& tip, const Vec2D& dimensions) : SDL_Rect{tip.x,tip.y,dimensions.x,dimensions.y}{}
  
//[API]
public:

  static Rect From_Corner_Points(int p0x, int p0y, int p1x, int p1y)
  {
		return From_Corner_Points(
    Vec2D(std::min(p0x,p1x),std::min(p0y,p1y)),
    Vec2D(std::max(p0x,p1x),std::max(p0y,p1y)));
	}
  
  static constexpr Rect From_Corner_Points(const Vec2D& p0, const Vec2D& p1)
  {
		return Rect(p0, p1 - p0 + Vec2D(1, 1));
	}
  
  bool operator==(const Rect& o)
  {
    return (this->x==o.x&&this->y==o.y&&this->w==o.w&&this->h==o.h);
  }
  
  bool operator!=(const Rect& o)
  {
    return !(*this==o);
  }
  
  int get_ratio()
  {
    return (h==0) ? w/h : 0;
  }
};

class Renderer;

class Texture
{
//[LIFECYCLE]
public:
  Texture(Renderer& renderer, Uint32 format, int access, int w, int h);

  virtual ~Texture();
  
  Texture(const Texture&) = delete;
  
  Texture& operator=(const Texture&) = delete;
  
  Texture(Texture&& o) noexcept;
  
  Texture& operator=(Texture&& o) noexcept;
  
//[API]
public:

  SDL_Texture* ptr() const;

  Texture& update(const void* pixels, int pitch);
  
  Texture& update_subarea(const SDL_Rect& rect, const void* pixels, int pitch);
  
  Texture& update_subarea(const Rect& rect, const void* pixels, int pitch);

  Uint32 get_format() const
  {
    Uint32 format;
    if (SDL_QueryTexture(texture, &format, nullptr, nullptr, nullptr) != 0)throw_sdl("SDL_QueryTexture");
    return format;
  }
//[INTERNAL]
private:
  SDL_Texture* texture;
};

Texture::~Texture()
{
  if (texture != nullptr)SDL_DestroyTexture(texture);
}

Texture::Texture(Texture&& o) noexcept : texture(o.texture)
{
	o.texture = nullptr;
}

Texture& Texture::operator=(Texture&& o) noexcept 
{
	if (&o == this)return *this;
	if (texture != nullptr)SDL_DestroyTexture(texture);
	texture = o.texture;
	o.texture = nullptr;
	return *this;
}

SDL_Texture* Texture::ptr() const 
{
	return texture;
}

Texture& Texture::update(const void* pixels, int pitch) 
{
	if (SDL_UpdateTexture(texture, nullptr, pixels, pitch) != 0)throw_sdl("UpdateTexture");
	return *this;
}

Texture& Texture::update_subarea(const SDL_Rect& subarea, const void* pixels, int pitch) 
{
	if (SDL_UpdateTexture(texture, &subarea, pixels, pitch) != 0)throw_sdl("UpdateTexture");
	return *this;
}

Texture& Texture::update_subarea(const Rect& subarea, const void* pixels, int pitch) 
{
	if (SDL_UpdateTexture(texture, &subarea, pixels, pitch) != 0)throw_sdl("UpdateTexture");
	return *this;
}


class PixelFormat
{
  
public:

  PixelFormat(Uint32 pixel_format_enum)
  {
    format = SDL_AllocFormat(pixel_format_enum);
    if( format == nullptr )throw_sdl("AllocFormat");
  }
  
  PixelFormat(const Texture& tex)
  {
    format = SDL_AllocFormat(tex.get_format());
    if( format == nullptr )throw_sdl("AllocFormat");
  }

  virtual ~PixelFormat()
  {
    if(format!=nullptr) SDL_FreeFormat(format);
  }
  
  PixelFormat(const PixelFormat&) = delete;
  
  PixelFormat& operator=(const PixelFormat&) = delete;
  
  PixelFormat(PixelFormat&& o)  = delete;
  
  PixelFormat& operator=(PixelFormat&& o)  = delete;
  
public:

  Uint32 map_rgba(Uint8 r, Uint8 g, Uint8 b,Uint8 a) const
  {
    return SDL_MapRGBA(format,r,g,b,a);
  }
  
  Uint32 map_rgba(const Color& color) const
  {
    return SDL_MapRGBA(format,color.r,color.g,color.b,color.a);
  }
private:
  SDL_PixelFormat* format;
};


class Renderer
{
//[LIFECYCLE]
public: 
  Renderer(Window& window);

  virtual ~Renderer();
  
  Renderer(const Renderer&) = delete;
  
  Renderer& operator=(const Renderer&) = delete;
  
  Renderer(Renderer&& o) noexcept;
  
  Renderer& operator=(Renderer&& o) noexcept;
//[API]
public:
  Renderer& present();
  
  Renderer& clear();
  
  Renderer& set_color(Uint8 r, Uint8 g, Uint8 b, Uint8 a);
  
  Renderer& set_color(const Color& color);
  
  Renderer& draw_line(int x0, int y0, int x1, int y1);
  
  Renderer& draw_line(const Vec2D& p0, const Vec2D& p1);

  Renderer& draw_lines(const Vec2D* points, int count);
  
  Renderer& draw_lines(const std::vector<Vec2D>& points);
  
  Renderer& draw_rect(const Rect& r);
  
  Renderer& fill_rect(const Rect& r);
  
  Renderer& draw_rects(const Rect* rects, int count);
  
  Renderer& draw_rects(const std::vector<Rect>& rects);
  
  Renderer& copy_fulltex_to_fullscreen(Texture& texture);

  Renderer& copy_fulltex_to_subscreen(Texture& texture, const Rect& dst_rect);

  Renderer& copy_subtex_to_fullscreen(Texture& texture, const Rect& src_rect);

  Renderer& copy_subtex_to_subscreen(Texture& texture, const Rect& src_rect, const Rect& dst_rect);
  
  SDL_Renderer* ptr() const;
//[INTERNAL]
private:
  SDL_Renderer* renderer;
};

Renderer::Renderer(Window& window)
{
  renderer = SDL_CreateRenderer(window.ptr(),-1,0);
  if( renderer == nullptr )throw_sdl("CreateRenderer");
}

Renderer::~Renderer(){if (renderer != nullptr)SDL_DestroyRenderer(renderer);}

Renderer::Renderer(Renderer&& o) noexcept : renderer(o.renderer) { o.renderer = nullptr; }
  
Renderer& Renderer::operator=(Renderer&& o) noexcept
{
	if (&o == this)return *this;
	if (renderer != nullptr)SDL_DestroyRenderer(renderer);
	renderer = o.renderer;
	o.renderer = nullptr;
	return *this;
}

Renderer& Renderer::present(){SDL_RenderPresent(renderer);return *this;}

Renderer& Renderer::clear()
{
  if (SDL_RenderClear(renderer) != 0)throw_sdl("RenderClear");
	return *this;
}

Renderer& Renderer::set_color(Uint8 r, Uint8 g, Uint8 b, Uint8 a)
{
  if (SDL_SetRenderDrawColor(renderer, r, g, b, a) != 0)throw_sdl("RenderSetDrawColor");
	return *this;
}

Renderer& Renderer::set_color(const Color& color)
{
  if (SDL_SetRenderDrawColor(renderer, color.r, color.g, color.b, color.a) != 0)throw_sdl("RenderSetDrawColor");
	return *this;
}

Renderer& Renderer::draw_line(int x0, int y0, int x1, int y1)
{
  if (SDL_RenderDrawLine(renderer, x0, y0, x1, y1) != 0)throw_sdl("RenderDrawLine");
	return *this;
}

Renderer& Renderer::draw_line(const Vec2D& p0, const Vec2D& p1)
{
  if (SDL_RenderDrawLine(renderer, p0.x, p0.y, p1.x, p1.y) != 0)throw_sdl("RenderDrawLine");
	return *this;
}

Renderer& Renderer::draw_lines(const Vec2D* points, int count)
{
	std::vector<SDL_Point> sdl_points;
	sdl_points.reserve(static_cast<size_t>(count));
	for (const Vec2D* p = points; p != points + count; ++p){sdl_points.emplace_back(*p);}
	if (SDL_RenderDrawLines(renderer, sdl_points.data(), count) != 0)throw_sdl("RenderDrawLines");
	return *this;
}

inline Renderer& Renderer::draw_lines(const std::vector<Vec2D>& points){ draw_lines(points.data(),points.size());}

Renderer& Renderer::draw_rect(const Rect& r)
{
	if (SDL_RenderDrawRect(renderer, &r) != 0)throw_sdl("RenderDrawRect");
	return *this;
}

Renderer& Renderer::fill_rect(const Rect& r)
{
	if (SDL_RenderFillRect(renderer, &r) != 0)throw_sdl("RenderFillRect");
	return *this;
}

Renderer& Renderer::draw_rects(const Rect* rects, int count)
{
  std::vector<SDL_Rect> sdl_rects;
	sdl_rects.reserve(static_cast<size_t>(count));
	for (const Rect* r = rects; r != rects + count; ++r){sdl_rects.emplace_back(*r);}
	if (SDL_RenderDrawRects(renderer, sdl_rects.data(), count) != 0)throw_sdl("RenderDrawRects");
	return *this;
}

inline Renderer& Renderer::draw_rects(const std::vector<Rect>& rects)
{
  draw_rects(rects.data(),rects.size());
}

Renderer& Renderer::copy_fulltex_to_fullscreen(Texture& texture) {
	if (SDL_RenderCopy(renderer, texture.ptr(), nullptr , nullptr ) != 0)throw_sdl("RenderCopy");
	return *this;
}

Renderer& Renderer::copy_fulltex_to_subscreen(Texture& texture, const Rect& dst_rect) {
	if (SDL_RenderCopy(renderer, texture.ptr(), nullptr , &dst_rect ) != 0)throw_sdl("RenderCopy");
	return *this;
}

Renderer& Renderer::copy_subtex_to_fullscreen(Texture& texture, const Rect& src_rect) {
	if (SDL_RenderCopy(renderer, texture.ptr(), &src_rect , nullptr ) != 0)throw_sdl("RenderCopy");
	return *this;
}

Renderer& Renderer::copy_subtex_to_subscreen(Texture& texture, const Rect& src_rect, const Rect& dst_rect) {
	if (SDL_RenderCopy(renderer, texture.ptr(), &src_rect , &dst_rect ) != 0)throw_sdl("RenderCopy");
	return *this;
}
  
SDL_Renderer* Renderer::ptr() const{ return renderer;};

Texture::Texture(Renderer& renderer, Uint32 format, int access, int w, int h)
{
	if ((texture = SDL_CreateTexture(renderer.ptr(), format, access, w, h)) == nullptr)throw_sdl("CreateTexture");
}

template<typename EvtStruct_t>
using Signal = Simple::Signal<void (const EvtStruct_t&)>;

class EventHandler
{
//[LIFECYCLE]
public:
  EventHandler();
  
  virtual ~EventHandler();
//[API]
public:
  bool poll();

  Signal<SDL_WindowEvent> sig_window;
  
  Signal<SDL_QuitEvent> sig_quit;
  
  Signal<SDL_KeyboardEvent> sig_key;
  
  Signal<SDL_MouseMotionEvent> sig_mousemotion;
  
  Signal<SDL_MouseButtonEvent> sig_mousebutton_down;
  
  Signal<SDL_MouseButtonEvent> sig_mousebutton_up;
//[INTERNAL]
private:
  virtual void handle(const SDL_Event& evt);
};

EventHandler::EventHandler(){}

EventHandler::~EventHandler(){}

bool EventHandler::poll()
{
  SDL_Event evt;
  if(SDL_PollEvent(&evt)){ 
    handle(evt);
    return true;
  }else{
    return false;
  }
}

void EventHandler::handle(const SDL_Event& evt)
{
  switch(evt.type){
    case SDL_WINDOWEVENT:
      sig_window.emit(evt.window);
      break;
    case SDL_QUIT:
      sig_quit.emit(evt.quit);
      break;
    case SDL_KEYUP:
      sig_key.emit(evt.key);
      break;
    case SDL_KEYDOWN:
      sig_key.emit(evt.key);
      break;
    case SDL_MOUSEMOTION:
      sig_mousemotion.emit(evt.motion);
      break;
    case SDL_MOUSEBUTTONUP:
      sig_mousebutton_up.emit(evt.button);
      break;
    case SDL_MOUSEBUTTONDOWN:
      sig_mousebutton_down.emit(evt.button);
      break;
    default:
      break;
  }
}


