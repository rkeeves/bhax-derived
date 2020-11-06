#lang racket

(define my-empty #f)

(define (my-cons a b)
  (λ (op) (op a b)))

(define (my-car ls)
  (ls (λ (a b) a)))

(define (my-cdr ls)
  (ls (λ (a b) b)))

(define (my-empty? ls)
  (equal? my-empty ls))

(define (my-length ls)
  (if (my-empty? ls) 0 (+ 1 (my-length (my-cdr ls)))))

(define (my-list . args)
  (let helper ([ls args])
    (if (empty? ls)
        my-empty
        (my-cons (car ls) (helper (cdr ls))))))

(define (my-build-list max proc)
  (let helper ([val 0])
    (if (< val max)
		(my-cons (proc val) (helper (add1 val)))
        my-empty)))

(define (my-rev ls)
  (let helper ([ls ls] [res my-empty])
    (if (my-empty? ls)
        res
        (helper (my-cdr ls) (my-cons (my-car ls) res)))))

(define (my-display ls)
  (display "(")
  (define (helper ls first)
  (cond
    [(my-empty? ls) (displayln ")")]
    [else (if first void (display " "))(display (my-car ls)) (helper (my-cdr ls)  #f)]))
  (helper ls #t))

(define (my-map proc ls)
  (let helper ([ls ls])
    (if (my-empty? ls)
        ls
        (my-cons (proc (my-car ls)) (helper (my-cdr ls))))))

(define (my-foldl proc init ls)
  (let helper ([ls ls] [accu init])
    (if (my-empty? ls)
        accu
        (helper (my-cdr ls) (proc (my-car ls) accu)))))

(define (my-for proc ls)
  (let helper ([ls ls])
    (cond [(my-empty? ls) (values)]
          [else (proc (my-car ls)) (helper (my-cdr ls))])))

(displayln "(my-list 1 2 3)")
(my-display (my-list 1 2 3))

(displayln "(my-build-list 10 values)")
(define test-ls (my-build-list 10 values))
(my-display test-ls)

(displayln "# All further examples will use the above list")

(displayln "(my-car test-ls)")
(my-car test-ls)

(displayln "(my-cdr test-ls)")
(my-display (my-cdr test-ls))

(displayln "(my-length test-ls)")
(my-length test-ls)

(displayln "(my-rev test-ls)")
(my-display (my-rev test-ls))

(displayln "(my-map add1 test-ls)")
(my-display (my-map add1 test-ls))

(displayln "(my-foldl + 0 test-ls)")
(my-foldl + 0 test-ls)

(displayln "(my-for display test-ls)")
(my-for display test-ls)