;Activity 3
;Daniel Atilano
;Benjamín Ruiz
;10/03/2021

#lang racket


(define (letter-distance letter)
    (if (eq? (char->integer letter) 32)
        26
        (- (char->integer letter) 97)))


(define (shift-char letter integer)
    (let
    ([limit (remainder integer 27)]) ;reduce every number entered in a 0-26 range
        (cond
            [(eq? (char->integer letter) 32)  
                (if (< limit 0)
                    (integer->char (+ 123 limit))
                    (integer->char (+ 96 limit))
                        )]
            [(or (< 122 (char->integer letter)) (< (char->integer letter) 97)) letter]    ;if a non-letter char is entered return char
            [(or (zero? limit) (zero? integer)) (integer->char (char->integer letter))]    ;if 0 or a whole lap is entered then return the same letter
            [(eq? (+ (letter-distance letter) 1) (* limit -1)) #\ ]
            [(< limit 0) (if (< (+ (char->integer letter) limit) 97)                       
                            (integer->char (+ (char->integer letter) (+ 27 limit)))  ;negative lower_case wrap  
                            (integer->char (+ (char->integer letter) limit)))]
            [(> limit 0) (if (> (+ (char->integer letter) limit) 122)                      
                            (integer->char (- (char->integer letter) (- 27 limit)))  ;positive lower_case wrap
                            (integer->char (+ (char->integer letter) limit)))])))              


(define (caesar-encode word integer decode)
    (let encode 
        ([result empty]
        [frozenlist (string->list word)]
        [integer integer])
            (cond 
                [(zero? integer) (list->string frozenlist)]
                [(if (empty? frozenlist)
                    (list->string result)   ;return either cypher or decyphered word when all chars have been shifted
                        (if (eq? decode #f)
                            (encode (append result (list (shift-char (car frozenlist) integer))) (cdr frozenlist) integer)
                            (encode (append result (list (shift-char (car frozenlist) (* integer -1)))) (cdr frozenlist) integer)))])))


(define (vigenere-encode word key decode)

    (define (resize-key word key)   ;function to match the key size to the word size
        (let resize
        ([word word]
        [key key]
        [result empty])
        (if (empty? word)          ;if done with every char, return bigger key
            result
            (if (empty? key)       ;if key goes empty add to it the first chars now stored in result
                (resize word (append key result) result)
                (resize (cdr word) (cdr key) (append result (list (car key))))))))

    (let encode 
        ([result empty]
        [key (if (< (string-length key)(string-length word))
             (resize-key (string->list word) (string->list key))
             (string->list key))]
        [word (string->list word)])
            (if (empty? word)
                (list->string result)   ;return either cypher or decyphered word when all chars have been shifted
                (if (eq? decode #f)
                    (encode (append result (list (shift-char (car word) (letter-distance (car key))))) (cdr key) (cdr word))
                    (encode (append result (list (shift-char (car word) (* (letter-distance (car key)) -1)))) (cdr key) (cdr word) )))))


;-------------One Time Pad------------------------

;Generate key of equal size to the document
(define (OneTimePad filename)
    (define in (open-input-file filename))
        (let loop
            ([char (read-char in)] 
            [key empty]
            [int (random 97 123)])
            (cond 
                [(eof-object? char) (OTPEncode filename (list->string key))]
                [else 
                    (loop (read-char in) (append key (list (integer->char int))) (random 97 123))])))

;Encrypt file with newly created key
(define (OTPEncode filename key)
    (define in (open-input-file filename))
    (define out (open-output-file "cypher.txt" #:exists 'truncate))
    (let loop
        ([line (read-line in)])
        (cond 
            [(eof-object? line) (close-input-port in) (close-output-port out) key]
            [else 
                (fprintf out "~a\n" (vigenere-encode line key #f))  
                (loop (read-line in))])))               
        


(define (encode-file filename-in filename-out function key decode)
    (define in (open-input-file filename-in))
    (define out (open-output-file filename-out #:exists 'truncate))
        (let loop
            ([line (read-line in)]
            [n 0])
            (cond 
                [(eof-object? line) (close-input-port in) (close-output-port out) n]
                [else 
                    (fprintf out "~a\n" (function line key decode))  ;print on the file either a cypher or decyphered line
                    (loop (read-line in) (add1 n))])))               ;keep reading lines until end of file
